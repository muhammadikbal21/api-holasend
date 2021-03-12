package com.enigmacamp.api.holasend.models.entitymodels.response;

import com.enigmacamp.api.holasend.enums.IdentityCategoryEnum;
import lombok.Data;

@Data
public class UserDetailsResponse {

    private String id;

    private IdentityCategoryEnum identityCategory;

    private String identificationNumber;

    private String firstName;

    private String lastName;

    private String contactNumber;
}
