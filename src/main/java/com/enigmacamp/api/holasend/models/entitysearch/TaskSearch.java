package com.enigmacamp.api.holasend.models.entitysearch;

import com.enigmacamp.api.holasend.enums.PriorityEnum;
import com.enigmacamp.api.holasend.enums.TaskStatusEnum;
import com.enigmacamp.api.holasend.models.pagination.PageSearch;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskSearch extends PageSearch {

    private LocalDateTime pickUpTime;

    private LocalDateTime deliveredTime;

    private TaskStatusEnum status;

    private PriorityEnum priority;
}
