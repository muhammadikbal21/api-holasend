package com.enigmacamp.api.holasend.models.entitymodels.response;

import lombok.Data;

@Data
public class DestinationResponse {

    private String id;

    private String name;

    private String address;

    private Float lon;

    private Float lat;
}