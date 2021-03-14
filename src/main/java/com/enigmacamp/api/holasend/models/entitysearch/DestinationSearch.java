package com.enigmacamp.api.holasend.models.entitysearch;

import com.enigmacamp.api.holasend.models.pagination.PageSearch;
import lombok.Data;

@Data
public class DestinationSearch extends PageSearch {

    private String name;

    private String address;

    private Float lon;

    private Float lat;
}