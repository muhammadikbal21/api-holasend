package com.enigmacamp.api.holasend.services.jwt;

import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.exceptions.InvalidCredentialsException;
import com.enigmacamp.api.holasend.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserJwtServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserJwtServiceImpl(UserRepository applicationUserRepository) {
        this.repository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User applicationUser = repository.findByUsername(username);
        if (applicationUser == null) {
            throw new InvalidCredentialsException();
        }
        return new org.springframework.security.core.userdetails.User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
    }
}
