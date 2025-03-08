package com.github.jaguzmanb1.quasar.service;

import com.github.jaguzmanb1.quasar.dto.SpaceShipInfoDTO;
import com.github.jaguzmanb1.quasar.dto.TopSecretResponseDTO;
import com.github.jaguzmanb1.quasar.entity.Satellite;
import com.github.jaguzmanb1.quasar.repository.SatelliteRepositoryInterface;
import com.github.jaguzmanb1.quasar.service.location.LocationCalculatorInterface;
import com.github.jaguzmanb1.quasar.service.messages.MessageDecoderInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TranslationServiceTest {

    @Mock
    private LocationCalculatorInterface locationCalculator;

    @Mock
    private MessageDecoderInterface messageDecoder;

    @Mock
    private SatelliteRepositoryInterface satelliteRepository;

    @InjectMocks
    private TranslationService translationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        translationService.init();
    }

    @Test
    public void testReturnTopSecretResponse() {
        List<SpaceShipInfoDTO> spaceShipInfo = Arrays.asList(
                new SpaceShipInfoDTO("kenobi", 1.0, Arrays.asList("this", "", "", "message")),
                new SpaceShipInfoDTO("skywalker", 1.0, Arrays.asList("", "is", "", "")),
                new SpaceShipInfoDTO("sato", 1.0, Arrays.asList("", "", "a", ""))
        );

        Point expectedLocation = new Point(0, 0);
        String expectedMessage = "this is a message";

       when(satelliteRepository.findAllByNameIn(Set.of("kenobi", "skywalker", "sato")))
           .thenReturn(Arrays.asList(
               new Satellite("kenobi", new Point(1, 0)),
               new Satellite("skywalker", new Point(-1, 0)),
               new Satellite("sato", new Point(0, 1))
           ));;

        when(locationCalculator.calculateLocation(
                new Point(1, 0),
                spaceShipInfo.get(0).getDistance(),
                new Point(-1, 0),
                spaceShipInfo.get(1).getDistance(),
                new Point(0, 1),
                spaceShipInfo.get(2).getDistance())
        ).thenReturn(expectedLocation);

        List<List<String>> messages = List.of(
                List.of("this", "", "", "message"),
                List.of("", "is", "", ""),
                List.of("", "", "a", "")
        );

        when(messageDecoder.decodeMessage(messages)).thenReturn(expectedMessage);

        TopSecretResponseDTO response = translationService.returnTopSecretResponse(spaceShipInfo);

        assertEquals(expectedLocation, response.getPosition());
        assertEquals(expectedMessage, response.getMessage());
    }

    @Test
    public void testReturnSplitTopSecretResponse() {
        Point expectedLocation = new Point(0, 0);
        String expectedMessage = "this is a message";

        when(satelliteRepository.findAllByNameIn(Set.of("kenobi", "skywalker", "sato")))
            .thenReturn(Arrays.asList(
                new Satellite("kenobi", new Point(1, 0), 1.0, Arrays.asList("this", "", "", "message")),
                new Satellite("skywalker", new Point(-1, 0), 1.0, Arrays.asList("", "is", "", "")),
                new Satellite("sato", new Point(0, 1), 1.0, Arrays.asList("", "", "a", ""))
            ));

        when(locationCalculator.calculateLocation(
                new Point(1, 0), 1.0,
                new Point(-1, 0), 1.0,
                new Point(0, 1), 1.0)
        ).thenReturn(expectedLocation);

        when(messageDecoder.decodeMessage(any())).thenReturn(expectedMessage);

        TopSecretResponseDTO response = translationService.returnSplitTopSecretResponse();

        assertEquals(expectedLocation, response.getPosition());
        assertEquals(expectedMessage, response.getMessage());
    }

    @Test
    public void testReturnTopSecretResponseWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            translationService.returnTopSecretResponse(null);
        });
    }

    @Test
    public void testReturnTopSecretResponseWithEmptyList() {
        assertThrows(IllegalArgumentException.class, () -> {
            translationService.returnTopSecretResponse(Collections.emptyList());
        });
    }

    @Test
    public void testReturnTopSecretResponseWithIncompleteData() {
        List<SpaceShipInfoDTO> spaceShipInfo = Arrays.asList(
                new SpaceShipInfoDTO("kenobi", 1.0, null),
                new SpaceShipInfoDTO("skywalker", 1.0, null)
        );

        assertThrows(IllegalArgumentException.class, () -> {
            translationService.returnTopSecretResponse(spaceShipInfo);
        });
    }
}