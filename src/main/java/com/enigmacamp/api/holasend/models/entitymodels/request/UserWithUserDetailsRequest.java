package com.enigmacamp.api.holasend.models.entitymodels.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserWithUserDetailsRequest {

    @NotNull
    private UserRequest user;

    @NotNull
    private UserDetailsRequest userDetails;
}
