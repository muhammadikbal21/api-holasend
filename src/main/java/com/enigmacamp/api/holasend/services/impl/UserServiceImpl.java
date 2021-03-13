package com.enigmacamp.api.holasend.services.impl;

import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JpaRepository<User, String> repository;

    @Override
    public User save(User entity) {
        return repository.save(entity);
    }

    @Override
    public User removeById(String id) {
        User entity = findById(id);
        if(entity != null) {
            repository.deleteById(id);
            return entity;
        } else {
            return null;
        }
    }

    @Override
    public User findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<User> findAll(User search, int page, int size, Sort.Direction direction) {
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
