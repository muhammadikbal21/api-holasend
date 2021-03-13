package com.enigmacamp.api.holasend.models.entitymodels.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class UserWithUserDetailsRequest {

    @Valid
    @NotNull
    private UserRequest user;

    @NotNull
    private UserDetailsRequest userDetails;
}
