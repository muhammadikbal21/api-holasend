package com.enigmacamp.api.holasend.models.entitymodels.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StatusCountResponse {

    private Long waiting;

    private Long assigned;

    private Long pickedUp;

    private Long delivered;

    private List<TaskResponse> lastRequest;
    private List<TaskResponse> lastPickup;
    private List<TaskResponse> lastDelivered;

}
