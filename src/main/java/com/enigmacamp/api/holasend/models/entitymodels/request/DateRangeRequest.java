package com.enigmacamp.api.holasend.models.entitymodels.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DateRangeRequest {

    @NotBlank
    private String start;

    @NotBlank
    private String end;
}
