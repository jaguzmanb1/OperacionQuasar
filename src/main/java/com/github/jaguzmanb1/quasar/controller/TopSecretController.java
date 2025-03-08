package com.github.jaguzmanb1.quasar.controller;

import com.github.jaguzmanb1.quasar.dto.SpaceShipInfoDTO;
import com.github.jaguzmanb1.quasar.dto.TopSecretResponseDTO;
import com.github.jaguzmanb1.quasar.dto.TopSecretRequestDTO;
import com.github.jaguzmanb1.quasar.service.TranslationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling requests related to the "Top Secret" operation.
 * This endpoint processes satellite data and returns the decoded message.
 */
@RestController
@RequestMapping("/")
public class TopSecretController {

    private final TranslationService translationService;

    /**
     * Constructor for TopSecretController.
     *
     * @param translationService the service responsible for processing satellite data
     */
    public TopSecretController(TranslationService translationService) {
        this.translationService = translationService;
    }

    /**
     * Processes a request containing satellite information and returns the decoded message.
     *
     * @param request the request containing the satellite data
     * @return a ResponseEntity containing the decoded message and response details
     */
    @PostMapping("/topsecret")
    public ResponseEntity<TopSecretResponseDTO> topSecret(@Valid @RequestBody TopSecretRequestDTO request) {
        TopSecretResponseDTO response = translationService.returnTopSecretResponse(request.getSatellites());
        return ResponseEntity.ok(response);
    }

    /**
     * Handles the request to update satellite information for a specific satellite.
     *
     * @param satelliteName the name of the satellite to update
     * @param spaceShipInfo the new information to update the satellite with
     * @return a ResponseEntity indicating the result of the update operation
     */
    @PostMapping("/topsecret_split/{satellite_name}")
    public ResponseEntity<Void> updateSatelliteInfo(
            @PathVariable("satellite_name") String satelliteName,
            @Valid @RequestBody SpaceShipInfoDTO spaceShipInfo) {
        translationService.updateSatelliteInfo(satelliteName, spaceShipInfo);
        return ResponseEntity.ok().build();
    }
}
