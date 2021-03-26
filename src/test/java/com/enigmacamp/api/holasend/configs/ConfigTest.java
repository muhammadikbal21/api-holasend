package com.enigmacamp.api.holasend.configs;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConfigTest {

    @Test
    void modelMapperShouldNotNull() {
        ApplicationConfig config = new ApplicationConfig();
        ModelMapper expected = config.modelMapper();

        assertNotNull(expected);
    }
}
