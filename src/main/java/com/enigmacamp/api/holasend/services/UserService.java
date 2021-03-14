package com.enigmacamp.api.holasend.services;

import com.enigmacamp.api.holasend.entities.User;

public interface UserService extends CommonService<User, String> {
    User findByUsername(String username);
    Boolean existsByUsername(String username);
}
