package com.enigmacamp.api.holasend.models.entitymodels.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CourierActivityResponse {

    private String id;
    private UserResponse courier;
    private LocalDate date;
    private LocalDateTime leavingTime;
    private LocalDateTime returnTime;
}
