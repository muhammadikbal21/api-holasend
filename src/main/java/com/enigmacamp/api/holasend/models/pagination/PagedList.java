package com.enigmacamp.api.holasend.models.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PagedList<T> {

    private List<T> list;
    private Integer page;
    private Integer size;
    private Long total;
}
