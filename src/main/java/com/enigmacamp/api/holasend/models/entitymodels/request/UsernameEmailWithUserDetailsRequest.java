package com.enigmacamp.api.holasend.models.entitymodels.request;

import com.enigmacamp.api.holasend.models.validations.Username;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UsernameEmailWithUserDetailsRequest {

    @Username
    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private UserDetailsRequest userDetails;
}
