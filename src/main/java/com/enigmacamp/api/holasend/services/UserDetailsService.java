package com.enigmacamp.api.holasend.services;

import com.enigmacamp.api.holasend.entities.UserDetails;

import java.util.List;

public interface UserDetailsService extends CommonService<UserDetails, String> {

    public UserDetails removeById(String id);

    public UserDetails findById(String id);

    public List<UserDetails> findAll();
}
