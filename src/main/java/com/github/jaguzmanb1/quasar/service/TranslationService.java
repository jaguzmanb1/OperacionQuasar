package com.github.jaguzmanb1.quasar.service;

import com.github.jaguzmanb1.quasar.dto.TopSecretResponseDTO;
import com.github.jaguzmanb1.quasar.entity.Satellite;
import com.github.jaguzmanb1.quasar.dto.SpaceShipInfoDTO;
import com.github.jaguzmanb1.quasar.service.location.LocationCalculatorInterface;
import com.github.jaguzmanb1.quasar.service.messages.MessageDecoderInterface;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    /**
     * Constructs a {@link TranslationService} with dependencies for location calculation and message decoding.
     *
     * @param locationCalculator The service responsible for computing positions via trilateration.
     * @param messageDecoder The service responsible for reconstructing messages from satellite signals.
     */
    @Autowired
    public TranslationService(LocationCalculatorInterface locationCalculator, MessageDecoderInterface messageDecoder) {
        this.locationCalculator = locationCalculator;
        this.messageDecoder = messageDecoder;
    }

    /**
     * Initializes the predefined satellite positions.
     * This method is automatically called after the bean is constructed.
     */
    @PostConstruct
    public void init() {
        this.satellites = new HashMap<>();
        this.satellites.put(KENOBI, new Satellite(KENOBI, new Point(-500, -200)));
        this.satellites.put(SKYWALKER, new Satellite(SKYWALKER, new Point(100, -100)));
        this.satellites.put(SATO, new Satellite(SATO, new Point(500, 100)));
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

        return locationCalculator.calculateLocation(
                satellites.get(KENOBI).getPosition(), distanceMap.get(KENOBI),
                satellites.get(SKYWALKER).getPosition(), distanceMap.get(SKYWALKER),
                satellites.get(SATO).getPosition(), distanceMap.get(SATO)
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