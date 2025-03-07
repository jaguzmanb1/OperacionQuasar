package com.github.jaguzmanb1.quasar.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * Data Transfer Object (DTO) for handling top-secret requests.
 * This DTO contains a list of satellite information used for location and message decoding.
 */
public class TopSecretRequestDTO {

    /**
     * List of satellites providing distance and message data.
     * The list must contain at least three elements and cannot be empty.
     */
    @NotEmpty(message = "The list of satellites cannot be empty")
    @Size(min = 3, message = "The list of satellites must have at least three elements")
    @Valid
    private List<SpaceShipInfoDTO> satellites;

    /**
     * Default constructor.
     */
    public TopSecretRequestDTO() {}

    /**
     * Constructor to initialize the request with a list of satellites.
     *
     * @param satellites the list of satellites containing distance and message data
     */
    public TopSecretRequestDTO(List<SpaceShipInfoDTO> satellites) {
        this.satellites = satellites;
    }

    /**
     * Gets the list of satellites.
     *
     * @return the list of satellites
     */
    public List<SpaceShipInfoDTO> getSatellites() {
        return satellites;
    }

    /**
     * Sets the list of satellites.
     *
     * @param satellites the new list of satellites
     */
    public void setSatellites(List<SpaceShipInfoDTO> satellites) {
        this.satellites = satellites;
    }
}
