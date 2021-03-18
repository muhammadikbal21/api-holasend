package com.enigmacamp.api.holasend.services;

import com.enigmacamp.api.holasend.entities.UserDetails;

import java.util.List;

public interface UserDetailsService extends CommonService<UserDetails, String> {

    UserDetails removeById(String id);

    UserDetails findById(String id);

    List<UserDetails> findAll();
}
