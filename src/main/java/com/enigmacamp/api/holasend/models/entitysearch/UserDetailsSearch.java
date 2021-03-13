package com.enigmacamp.api.holasend.models.entitysearch;

import com.enigmacamp.api.holasend.enums.IdentityCategoryEnum;
import com.enigmacamp.api.holasend.models.pagination.PageSearch;
import lombok.Data;

@Data
public class UserDetailsSearch extends PageSearch {

    private IdentityCategoryEnum identityCategory;

    private String identificationNumber;

    private String firstName;

    private String lastName;

    private String contactNumber;
}
