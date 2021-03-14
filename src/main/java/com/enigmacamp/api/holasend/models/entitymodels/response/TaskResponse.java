package com.enigmacamp.api.holasend.models.entitymodels.response;

import com.enigmacamp.api.holasend.entities.Destination;
import com.enigmacamp.api.holasend.enums.PriorityEnum;
import com.enigmacamp.api.holasend.enums.TaskStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponse {

    private String id;

    private DestinationResponse destination;

    private UserResponse requestBy;

    private UserResponse courier;

    private LocalDateTime pickUpTime;

    private LocalDateTime deliveredTime;

    private TaskStatusEnum status;

    private PriorityEnum priority;

    private String notes;
}
