package com.enigmacamp.api.holasend.models.entitymodels.elements;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CourierActivityElement {

    private String id;
    private LocalDate date;
    private LocalDateTime leavingTime;
    private LocalDateTime returnTime;
}
