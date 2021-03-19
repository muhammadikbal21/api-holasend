package com.enigmacamp.api.holasend.models.entitysearch;

import com.enigmacamp.api.holasend.enums.PriorityEnum;
import com.enigmacamp.api.holasend.enums.TaskStatusEnum;
import com.enigmacamp.api.holasend.models.pagination.PageSearch;
import lombok.Data;

import java.util.Date;

@Data
public class TaskSearch extends PageSearch {

    private TaskStatusEnum status;

    private PriorityEnum priority;
}
