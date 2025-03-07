package com.github.jaguzmanb1.quasar.service;

import com.github.jaguzmanb1.quasar.dto.SpaceShipInfoDTO;
import com.github.jaguzmanb1.quasar.dto.TopSecretResponseDTO;
import com.github.jaguzmanb1.quasar.service.location.LocationCalculatorInterface;
import com.github.jaguzmanb1.quasar.service.messages.MessageDecoderInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class TranslationServiceTest {

    @Mock
    private LocationCalculatorInterface locationCalculator;

    @Mock
    private MessageDecoderInterface messageDecoder;

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
                new SpaceShipInfoDTO("kenobi", 100.0, Arrays.asList("this", "", "", "message")),
                new SpaceShipInfoDTO("skywalker", 115.5, Arrays.asList("", "is", "", "")),
                new SpaceShipInfoDTO("sato", 142.7, Arrays.asList("", "", "a", ""))
        );

        Point expectedLocation = new Point(100, 200);
        String expectedMessage = "this is a message";

        when(locationCalculator.calculateLocation(
                new Point(-500, -200),
                spaceShipInfo.get(0).getDistance(),
                new Point(100, -100),
                spaceShipInfo.get(1).getDistance(),
                new Point(500, 100),
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
                new SpaceShipInfoDTO("kenobi", 100.0, null),
                new SpaceShipInfoDTO("skywalker", 115.5, null)
        );

        assertThrows(IllegalArgumentException.class, () -> {
            translationService.returnTopSecretResponse(spaceShipInfo);
        });
    }
}