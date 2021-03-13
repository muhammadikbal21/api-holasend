package com.enigmacamp.api.holasend.models.pagination;

import lombok.Data;
import org.springframework.data.domain.Sort.Direction;

import javax.validation.constraints.Max;

@Data
public class PageSearch {

    private Integer page = 0;

    @Max(Integer.MAX_VALUE)
    private Integer size = 10;

    private Direction sort = Direction.ASC;
}
