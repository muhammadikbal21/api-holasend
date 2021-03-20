package com.enigmacamp.api.holasend.models.entitysearch;

import com.enigmacamp.api.holasend.models.pagination.PageSearch;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskSearch extends PageSearch {

    private String status;

    private String destinationId = "";

    private String requestById = "";

    private String priority;

    private String after = "2021-01-01";

    private String before = LocalDateTime.now().plusDays(1).toString().substring(0, 10);
}
