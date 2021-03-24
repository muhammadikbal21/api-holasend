package com.enigmacamp.api.holasend.models.entitymodels.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleCountResponse {

    private Long admin;

    private Long staff;

    private Long courier;

    private Long unassigned;

    private Long disabled;
}
