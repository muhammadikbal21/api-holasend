package com.enigmacamp.api.holasend.services.impl;

import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.services.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends CommonServiceImpl<User, String>implements UserService {
    protected UserServiceImpl(JpaRepository<User, String> repository) {
        super(repository);
    }
}
