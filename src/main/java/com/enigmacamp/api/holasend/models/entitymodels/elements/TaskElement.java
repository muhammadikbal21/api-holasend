package com.enigmacamp.api.holasend.models.entitymodels.elements;

import com.enigmacamp.api.holasend.enums.PriorityEnum;
import com.enigmacamp.api.holasend.enums.TaskStatusEnum;
import com.enigmacamp.api.holasend.models.entitymodels.response.DestinationResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskElement {

    private String id;

    private DestinationResponse destination;

    private LocalDateTime pickUpTime;

    private LocalDateTime deliveredTime;

    private TaskStatusEnum status;

    private PriorityEnum priority;

    private String notes;

    private LocalDateTime createDate;
}
