package com.enigmacamp.api.holasend.models;

import com.enigmacamp.api.holasend.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenWithRoleModel {
    private String token;
    private RoleEnum role;
}
