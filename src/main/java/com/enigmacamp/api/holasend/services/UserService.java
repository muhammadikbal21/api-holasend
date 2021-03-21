package com.enigmacamp.api.holasend.services;

import com.enigmacamp.api.holasend.entities.User;

import java.util.List;

public interface UserService extends CommonService<User, String> {

    User removeById(String id);

    User findById(String id);

    List<User> findAll();
    List<User> findOnlyStaffOrAdmin();

    User findByUsername(String username);
    Boolean existsByUsername(String username);
}
