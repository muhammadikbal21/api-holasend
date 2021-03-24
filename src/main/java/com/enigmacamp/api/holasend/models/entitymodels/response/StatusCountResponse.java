package com.enigmacamp.api.holasend.models.entitymodels.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusCountResponse {

    private Long waiting;

    private Long assigned;

    private Long pickedUp;

    private Long delivered;
}
