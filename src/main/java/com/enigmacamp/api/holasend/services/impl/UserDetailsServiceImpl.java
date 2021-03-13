package com.enigmacamp.api.holasend.services.impl;

import com.enigmacamp.api.holasend.entities.UserDetails;
import com.enigmacamp.api.holasend.services.UserDetailsService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl extends CommonServiceImpl<UserDetails, String> implements UserDetailsService {

    protected UserDetailsServiceImpl(JpaRepository<UserDetails, String> repository) {
        super(repository);
    }
}
