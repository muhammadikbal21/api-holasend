package com.enigmacamp.api.holasend.models;

import com.enigmacamp.api.holasend.models.converters.DirectionConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConverterTest {

    private final DirectionConverter converter = new DirectionConverter();

    @Test
    void directionConverterShouldReturnUppercase() {
        assertEquals(converter.convert("ASC"), converter.convert("asc"));
    }
}
