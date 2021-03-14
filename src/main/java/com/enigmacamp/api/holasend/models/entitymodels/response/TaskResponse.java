package com.enigmacamp.api.holasend.models.entitymodels.response;

import com.enigmacamp.api.holasend.entities.Destination;
import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.enums.PriorityEnum;
import com.enigmacamp.api.holasend.enums.TaskStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponse {

    private String id;

    private Destination destination;

    private User requestBy;

    private User courier;

    private LocalDateTime pickUpTime;

    private LocalDateTime deliveredTime;

    private TaskStatusEnum status;

    private PriorityEnum priority;

    private String notes;
}
