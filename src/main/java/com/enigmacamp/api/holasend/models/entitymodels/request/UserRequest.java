package com.enigmacamp.api.holasend.models.entitymodels.request;

import com.enigmacamp.api.holasend.models.validations.Password;
import com.enigmacamp.api.holasend.models.validations.Username;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserRequest {

    /**Must match:
     * min 6 characters,
     * max 20 characters*/
    @Username
    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    /**Must match:
     * 1 uppercase,
     * 1 lowercase,
     * min 8 characters*/
    @Password
    @NotBlank
    private String password;
}
