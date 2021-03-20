package com.enigmacamp.api.holasend.models.pagination;

import lombok.Data;
import org.springframework.data.domain.Sort.Direction;

import javax.validation.constraints.Max;

@Data
public class PageSearch {

    private Long page = 0L;

    @Max(Long.MAX_VALUE)
    private Long size = 10L;

    private Direction sort = Direction.ASC;
}
