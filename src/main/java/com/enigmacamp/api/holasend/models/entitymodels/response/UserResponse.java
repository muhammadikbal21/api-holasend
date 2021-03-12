package com.enigmacamp.api.holasend.models.entitymodels.response;

import com.enigmacamp.api.holasend.enums.RoleEnum;
import lombok.Data;

@Data
public class UserResponse {

    private String id;

    private String username;

    private String email;

    private RoleEnum role;

    private UserDetailsResponse userDetails;
}
