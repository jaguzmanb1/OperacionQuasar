package com.github.jaguzmanb1.quasar.controller;

import com.github.jaguzmanb1.quasar.dto.SpaceShipInfoDTO;
import com.github.jaguzmanb1.quasar.dto.TopSecretResponseDTO;
import com.github.jaguzmanb1.quasar.dto.TopSecretRequestDTO;
import com.github.jaguzmanb1.quasar.entity.Satellite;
import com.github.jaguzmanb1.quasar.service.TranslationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for handling requests related to the "Top Secret" operation.
 * This controller processes satellite data and provides decoded messages
 * and spaceship location information.
 */
@RestController
@RequestMapping("/")
public class TopSecretController {

    private final TranslationService translationService;

    /**
     * Constructor for TopSecretController.
     *
     * @param translationService The service responsible for processing satellite data.
     */
    public TopSecretController(TranslationService translationService) {
        this.translationService = translationService;
    }

    /**
     * Receives satellite data and determines the spaceship's location and decoded message.
     *
     * @param request The request containing a list of satellite data.
     * @return ResponseEntity containing the decoded message and calculated location.
     */
    @PostMapping("/topsecret")
    public ResponseEntity<TopSecretResponseDTO> topSecret(@Valid @RequestBody TopSecretRequestDTO request) {
        TopSecretResponseDTO response = translationService.returnTopSecretResponse(request.getSatellites());
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a list of all available satellites from the database.
     *
     * @return ResponseEntity containing the list of stored satellites.
     */
    @GetMapping("/satellites")
    public ResponseEntity<List<Satellite>> getSatellites() {
        return ResponseEntity.ok(translationService.getSatellites());
    }

    /**
     * Updates the stored data of a specific satellite.
     * This is part of the "/topsecret_split" flow, where satellite data is received separately.
     *
     * @param satelliteName The name of the satellite to update (Kenobi, Skywalker, or Sato).
     * @param spaceShipInfo The new data containing distance and received message.
     * @return ResponseEntity with a success message.
     */
    @PostMapping("/topsecret_split/{satellite_name}")
    public ResponseEntity<Map<String, String>> updateSatelliteInfo(
            @PathVariable("satellite_name")
            @Pattern(regexp = "(?i)kenobi|skywalker|sato", message = "Invalid satellite name") String satelliteName,
            @Valid @RequestBody SpaceShipInfoDTO spaceShipInfo) {

        translationService.updateSatelliteInfo(satelliteName, spaceShipInfo);
        return ResponseEntity.ok(Map.of("message", "OK"));
    }

    /**
     * Processes the stored satellite data and determines the spaceship's location
     * and decoded message from previously stored distances and messages.
     *
     * @return ResponseEntity containing the decoded message and calculated location.
     */
    @GetMapping("/topsecret_split")
    public ResponseEntity<TopSecretResponseDTO> getSatelliteSplitInfo() {
        return ResponseEntity.ok(translationService.returnSplitTopSecretResponse());
    }
}