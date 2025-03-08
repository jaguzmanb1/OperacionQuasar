package com.github.jaguzmanb1.quasar.service;

import com.github.jaguzmanb1.quasar.dto.TopSecretResponseDTO;
import com.github.jaguzmanb1.quasar.dto.SpaceShipInfoDTO;
import com.github.jaguzmanb1.quasar.entity.Satellite;
import com.github.jaguzmanb1.quasar.repository.SatelliteRepositoryInterface;
import com.github.jaguzmanb1.quasar.service.location.LocationCalculatorInterface;
import com.github.jaguzmanb1.quasar.service.messages.MessageDecoderInterface;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service responsible for managing satellite data, calculating the spaceship's location,
 * and decoding transmitted messages.
 */
@Service
public class TranslationService {
    private static final String KENOBI = "kenobi";
    private static final String SKYWALKER = "skywalker";
    private static final String SATO = "sato";
    private static final Set<String> REQUIRED_SATELLITES = Set.of(KENOBI, SKYWALKER, SATO);

    private final LocationCalculatorInterface locationCalculator;
    private final MessageDecoderInterface messageDecoder;
    private final SatelliteRepositoryInterface satelliteRepository;

    /**
     * Constructor to inject dependencies.
     * @param locationCalculator Interface for calculating the spaceship location.
     * @param messageDecoder Interface for decoding intercepted messages.
     * @param satelliteRepository Repository interface for accessing satellite data.
     */
    @Autowired
    public TranslationService(LocationCalculatorInterface locationCalculator,
                              MessageDecoderInterface messageDecoder,
                              SatelliteRepositoryInterface satelliteRepository) {
        this.locationCalculator = locationCalculator;
        this.messageDecoder = messageDecoder;
        this.satelliteRepository = satelliteRepository;
    }

    /**
     * Initializes the satellite repository with default satellite positions if no data is present.
     */
    @PostConstruct
    public void init() {
        if (satelliteRepository.count() == 0) {
            satelliteRepository.saveAll(List.of(
                    new Satellite(KENOBI, new Point(-500, -200)),
                    new Satellite(SKYWALKER, new Point(100, -100)),
                    new Satellite(SATO, new Point(500, 100))
            ));
        }
    }

    /**
     * Updates the satellite's stored distance and message information.
     * @param satelliteName The name of the satellite to update.
     * @param spaceShipInfo The received data containing distance and message.
     */
    public void updateSatelliteInfo(String satelliteName, SpaceShipInfoDTO spaceShipInfo) {
        Satellite satellite = satelliteRepository.findByName(satelliteName)
                .orElseThrow(() -> new IllegalArgumentException("Satellite not found: " + satelliteName));

        satellite.setDistance(spaceShipInfo.getDistance());
        satellite.setReceivedMessage(spaceShipInfo.getMessage());
        satelliteRepository.save(satellite);
    }

    /**
     * Retrieves all satellites from the database.
     * @return List of all satellites.
     */
    public List<Satellite> getSatellites() {
        return satelliteRepository.findAll();
    }

    /**
     * Determines the spaceship's location and deciphers its message using given satellite data.
     * @param spaceShipDistanceInfo List of received distances and messages from satellites.
     * @return Decoded spaceship message and estimated location.
     */
    public TopSecretResponseDTO returnTopSecretResponse(List<SpaceShipInfoDTO> spaceShipDistanceInfo) {
        if (spaceShipDistanceInfo == null || spaceShipDistanceInfo.size() < 3) {
            throw new IllegalArgumentException("At least three satellites are required.");
        }

        Map<String, Double> distanceMap = spaceShipDistanceInfo.stream()
                .collect(Collectors.toMap(SpaceShipInfoDTO::getName, SpaceShipInfoDTO::getDistance));

        List<Satellite> satellites = fetchAndValidateSatellites(distanceMap.keySet(), false);

        Point location = locationCalculator.calculateLocation(
                satellites.get(0).getPosition(), distanceMap.get(satellites.get(0).getName()),
                satellites.get(1).getPosition(), distanceMap.get(satellites.get(1).getName()),
                satellites.get(2).getPosition(), distanceMap.get(satellites.get(2).getName())
        );

        String message = messageDecoder.decodeMessage(
                spaceShipDistanceInfo.stream().map(SpaceShipInfoDTO::getMessage).toList());

        return new TopSecretResponseDTO(location, message);
    }

    /**
     * Retrieves stored satellite data and determines the spaceship's location and message.
     * @return Decoded spaceship message and estimated location.
     */
    public TopSecretResponseDTO returnSplitTopSecretResponse() {
        List<Satellite> satellites = fetchAndValidateSatellites(REQUIRED_SATELLITES, true);

        Point location = locationCalculator.calculateLocation(
                satellites.get(0).getPosition(), satellites.get(0).getDistance(),
                satellites.get(1).getPosition(), satellites.get(1).getDistance(),
                satellites.get(2).getPosition(), satellites.get(2).getDistance()
        );

        List<List<String>> messagesList = satellites.stream().map(Satellite::getReceivedMessage).toList();

        String message = messageDecoder.decodeMessage(
                messagesList
        );

        return new TopSecretResponseDTO(location, message);
    }

    /**
     * Fetches and validates satellite data based on provided names.
     * Ensures that all required satellites exist and have valid data.
     * @param requiredSatellites The set of satellite names that need to be validated.
     * @return List of valid satellites.
     */
    private List<Satellite> fetchAndValidateSatellites(Set<String> requiredSatellites, boolean splitMode) {
        List<Satellite> satellites = satelliteRepository.findAllByNameIn(requiredSatellites);
        if (satellites.size() < 3) {
            throw new IllegalArgumentException("Missing required satellites in database.");
        }

        if (!splitMode) {
            return satellites;
        }

        for (Satellite sat : satellites) {
            if (sat.getDistance() == null || sat.getDistance() <= 0) {
                throw new IllegalArgumentException("Satellite " + sat.getName() + " is missing distance data.");
            }
            if (sat.getReceivedMessage() == null || sat.getReceivedMessage().isEmpty()) {
                throw new IllegalArgumentException("Satellite " + sat.getName() + " is missing message data.");
            }
        }

        return satellites;
    }
}