package com.enigmacamp.api.holasend.models.entitysearch;

import com.enigmacamp.api.holasend.models.pagination.PageSearch;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyRequestTaskSearch extends PageSearch {

    private String destinationId = "";

    private String priority;

    private String after = "2021-01-01";

    private String before = LocalDateTime.now().plusDays(1).toString().substring(0, 10);
}
