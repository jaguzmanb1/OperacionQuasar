package com.github.jaguzmanb1.quasar.service;

import com.github.jaguzmanb1.quasar.dto.TopSecretResponseDTO;
import com.github.jaguzmanb1.quasar.entity.Satellite;
import com.github.jaguzmanb1.quasar.dto.SpaceShipInfoDTO;
import com.github.jaguzmanb1.quasar.repository.SatelliteRepositoryInterface;
import com.github.jaguzmanb1.quasar.service.location.LocationCalculatorInterface;
import com.github.jaguzmanb1.quasar.service.messages.MessageDecoderInterface;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsible for processing satellite data to determine the spaceship's location and message.
 * This service orchestrates trilateration for position calculation and message reconstruction based on received signals.
 */
@Service
public class TranslationService {

    private static final String KENOBI = "kenobi";
    private static final String SKYWALKER = "skywalker";
    private static final String SATO = "sato";
    private static final Set<String> REQUIRED_SATELLITES = Set.of(KENOBI, SKYWALKER, SATO);
    private Map<String, Satellite> satellites;

    private final LocationCalculatorInterface locationCalculator;
    private final MessageDecoderInterface messageDecoder;

    private final SatelliteRepositoryInterface satelliteRepository;

    /**
     * Constructs a {@link TranslationService} with dependencies for location calculation and message decoding.
     *
     * @param locationCalculator The service responsible for computing positions via trilateration.
     * @param messageDecoder The service responsible for reconstructing messages from satellite signals.
     */
    @Autowired
    public TranslationService(
            LocationCalculatorInterface locationCalculator,
            MessageDecoderInterface messageDecoder,
            SatelliteRepositoryInterface satelliteRepository) {
        this.locationCalculator = locationCalculator;
        this.messageDecoder = messageDecoder;
        this.satelliteRepository = satelliteRepository;
    }

    /**
     * Initializes the predefined satellite positions.
     * This method is automatically called after the bean is constructed.
     */
    @PostConstruct
    public void init() {
        if (satelliteRepository.count() == 0) { // Evita insertar duplicados
            List<Satellite> satellites = Arrays.asList(
                    new Satellite("Kenobi", new Point(-500, -200)),
                    new Satellite("Skywalker", new Point(100, -100)),
                    new Satellite("Sato", new Point(500, 100))
            );
            satelliteRepository.saveAll(satellites);
        }
    }

    /**
     * Updates the satellite information with the provided data.
     *
     * @param satelliteName the name of the satellite to update
     * @param spaceShipInfo the new information to update the satellite with
     * @throws IllegalArgumentException if the satellite name is null or empty, or if the spaceShipInfo is null
     */
    public void updateSatelliteInfo(String satelliteName, SpaceShipInfoDTO spaceShipInfo) {
        if (satelliteName == null || satelliteName.isEmpty()) {
            throw new IllegalArgumentException("Satellite name cannot be null or empty");
        }
        if (spaceShipInfo == null) {
            throw new IllegalArgumentException("SpaceShipInfoDTO cannot be null");
        }

        satelliteRepository.updateDistanceAndMessageByName(
                satelliteName,
                spaceShipInfo.getDistance(),
                convertListToString(spaceShipInfo.getMessage())
        );
    }

    private String convertListToString(List<String> messages) {
        return (messages != null && !messages.isEmpty()) ? String.join(",", messages) : null;
    }

    private List<String> convertStringToList(String messageString) {
        return (messageString != null && !messageString.isEmpty()) ? Arrays.asList(messageString.split(",")) : null;
    }

    /**
     * Processes the received satellite data and returns the calculated location and message.
     *
     * @param spaceShipDistanceInfo List of {@link SpaceShipInfoDTO} containing satellite distance and message data.
     * @return A {@link TopSecretResponseDTO} containing the computed position and decoded message.
     * @throws IllegalArgumentException if the provided satellite data is incomplete or invalid.
     */
    public TopSecretResponseDTO returnTopSecretResponse(List<SpaceShipInfoDTO> spaceShipDistanceInfo) {
        validateInput(spaceShipDistanceInfo);
        Point location = getLocation(spaceShipDistanceInfo);
        String message = getMessage(spaceShipDistanceInfo);

        return new TopSecretResponseDTO(location, message);
    }

    /**
     * Validates the input data to ensure all required satellites are present and at least three elements exist.
     *
     * @param spaceShipDistanceInfo List of {@link SpaceShipInfoDTO} containing satellite data.
     * @throws IllegalArgumentException if the input data does not meet the required conditions.
     */
    private void validateInput(List<SpaceShipInfoDTO> spaceShipDistanceInfo) {
        if (spaceShipDistanceInfo == null || spaceShipDistanceInfo.size() < 3) {
            throw new IllegalArgumentException("The satellite data must contain at least three entries.");
        }

        Set<String> receivedSatellites = spaceShipDistanceInfo.stream()
                .map(SpaceShipInfoDTO::getName)
                .collect(Collectors.toSet());

        if (!receivedSatellites.containsAll(REQUIRED_SATELLITES)) {
            throw new IllegalArgumentException("Missing required satellites. Expected: " + REQUIRED_SATELLITES);
        }
    }

    /**
     * Calculates the spaceship's location using trilateration based on distances from satellites.
     *
     * @param spaceShipDistanceInfo List of {@link SpaceShipInfoDTO} containing distance information.
     * @return The computed location as a {@link Point} object.
     * @throws IllegalArgumentException if there are missing or unknown satellites.
     */
    private Point getLocation(List<SpaceShipInfoDTO> spaceShipDistanceInfo) {
        Map<String, Double> distanceMap = new HashMap<>();
        for (SpaceShipInfoDTO info : spaceShipDistanceInfo) {
            distanceMap.put(info.getName(), info.getDistance());
        }

        List<Satellite> satellites = satelliteRepository.findAll();
        Map<String, Satellite> satelliteMap = satellites.stream()
                .collect(Collectors.toMap(Satellite::getName, satellite -> satellite));

        return locationCalculator.calculateLocation(
            satelliteMap.get(KENOBI).getPosition(), distanceMap.get(KENOBI),
            satelliteMap.get(SKYWALKER).getPosition(), distanceMap.get(SKYWALKER),
            satelliteMap.get(SATO).getPosition(), distanceMap.get(SATO)
        );
    }

    /**
     * Merges received fragmented messages from different satellites into a single coherent message.
     *
     * @param spaceShipDistanceInfo List of {@link SpaceShipInfoDTO} containing message fragments.
     * @return The reconstructed message.
     * @throws IllegalArgumentException if the input data is null or invalid.
     */
    private String getMessage(List<SpaceShipInfoDTO> spaceShipDistanceInfo) {
        List<List<String>> messages = spaceShipDistanceInfo.stream()
                .map(SpaceShipInfoDTO::getMessage)
                .toList();

        return messageDecoder.decodeMessage(messages);
    }
}