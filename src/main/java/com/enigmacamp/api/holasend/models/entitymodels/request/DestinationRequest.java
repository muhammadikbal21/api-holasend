package com.enigmacamp.api.holasend.models.entitymodels.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DestinationRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    private Float lon;

    @NotNull
    private Float lat;
}