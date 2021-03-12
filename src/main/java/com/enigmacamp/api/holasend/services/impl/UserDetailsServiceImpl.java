package com.enigmacamp.api.holasend.services.impl;

import com.enigmacamp.api.holasend.entities.UserDetails;
import com.enigmacamp.api.holasend.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private JpaRepository<UserDetails, String> repository;

    @Override
    public UserDetails save(UserDetails entity) {
        return repository.save(entity);
    }

    @Override
    public UserDetails removeById(String id) {
        UserDetails entity = findById(id);
        if(entity != null) {
            repository.deleteById(id);
            return entity;
        } else {
            return null;
        }
    }

    @Override
    public UserDetails findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<UserDetails> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<UserDetails> findAll(UserDetails search, int page, int size, Sort.Direction direction) {
        Sort sort = Sort.Direction.DESC.equals(direction) ? Sort.by(direction, "id") : Sort.by("id");
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        return repository.findAll(
                Example.of(search, matcher),
                PageRequest.of(page, size, sort)
        );
    }
}
