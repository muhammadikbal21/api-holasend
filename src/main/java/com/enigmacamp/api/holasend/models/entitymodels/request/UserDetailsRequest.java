package com.enigmacamp.api.holasend.models.entitymodels.request;

import com.enigmacamp.api.holasend.enums.IdentityCategoryEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserDetailsRequest {

    @NotNull
    private IdentityCategoryEnum identityCategory;

    @NotBlank
    private String identificationNumber;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String contactNumber;
}
