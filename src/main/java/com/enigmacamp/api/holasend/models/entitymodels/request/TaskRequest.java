package com.enigmacamp.api.holasend.models.entitymodels.request;

import com.enigmacamp.api.holasend.enums.PriorityEnum;
import com.enigmacamp.api.holasend.enums.TaskStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequest {

    private String destinationId;

    private String requestById;

    private String courierId;

    private LocalDateTime pickUpTime;

    private LocalDateTime deliveredTime;

    private TaskStatusEnum status;

    private PriorityEnum priority;

    private String notes;
}
