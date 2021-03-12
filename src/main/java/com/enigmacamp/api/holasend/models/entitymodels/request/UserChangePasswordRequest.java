package com.enigmacamp.api.holasend.models.entitymodels.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserChangePasswordRequest {

    @NotBlank
    private String password;
}
