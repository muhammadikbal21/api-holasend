package com.enigmacamp.api.holasend.services;

import com.enigmacamp.api.holasend.entities.User;

import java.util.List;

public interface UserService extends CommonService<User, String> {

    public User removeById(String id);

    public User findById(String id);

    public List<User> findAll();

    User findByUsername(String username);
    Boolean existsByUsername(String username);
}
