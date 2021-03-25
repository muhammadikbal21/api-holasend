package com.enigmacamp.api.holasend.models;

import com.enigmacamp.api.holasend.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenWithUsernameAndRoleModel {
    private String token;
    private String username;
    private RoleEnum role;
}
