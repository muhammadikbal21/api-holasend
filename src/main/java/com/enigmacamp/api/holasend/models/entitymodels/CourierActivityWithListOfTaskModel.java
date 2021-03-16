package com.enigmacamp.api.holasend.models.entitymodels;

import com.enigmacamp.api.holasend.models.entitymodels.response.TaskResponse;
import com.enigmacamp.api.holasend.models.entitymodels.response.UserResponse;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CourierActivityWithListOfTaskModel {

    private String id;
    private UserResponse courier;
    private LocalDate date;
    private LocalDateTime leavingTime;
    private LocalDateTime returnTime;
    private List<TaskResponse> taskList;
}
