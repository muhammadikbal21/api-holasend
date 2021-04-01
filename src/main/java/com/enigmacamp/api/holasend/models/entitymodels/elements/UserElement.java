package com.enigmacamp.api.holasend.models.entitymodels.elements;

import com.enigmacamp.api.holasend.enums.RoleEnum;
import com.enigmacamp.api.holasend.models.entitymodels.response.UserDetailsResponse;
import lombok.Data;

@Data
public class UserElement {

    private String id;

    private String username;

    private String email;

    private RoleEnum role;

    private UserDetailsResponse userDetails;
}
