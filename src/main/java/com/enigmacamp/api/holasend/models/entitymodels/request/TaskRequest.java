package com.enigmacamp.api.holasend.models.entitymodels.request;

import com.enigmacamp.api.holasend.enums.PriorityEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TaskRequest {

    @NotNull
    private String destinationId;

    @NotNull
    private PriorityEnum priority;

    @NotBlank
    private String notes;
}
