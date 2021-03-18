package com.enigmacamp.api.holasend.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountModel {
    private Long waiting;
    private Long assigned;
    private Long pickedUp;
    private Long delivered;
}
