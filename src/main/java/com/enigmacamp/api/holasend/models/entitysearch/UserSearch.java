package com.enigmacamp.api.holasend.models.entitysearch;

import com.enigmacamp.api.holasend.enums.RoleEnum;
import com.enigmacamp.api.holasend.models.pagination.PageSearch;
import lombok.Data;

@Data
public class UserSearch extends PageSearch {

    private String username;

    private String email;

    private RoleEnum role;
}
