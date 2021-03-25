package com.enigmacamp.api.holasend.services.impl;

import com.enigmacamp.api.holasend.entities.UserDetails;
import com.enigmacamp.api.holasend.exceptions.EntityNotFoundException;
import com.enigmacamp.api.holasend.repositories.UserDetailsRepository;
import com.enigmacamp.api.holasend.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl extends CommonServiceImpl<UserDetails, String> implements UserDetailsService {

    protected UserDetailsServiceImpl(JpaRepository<UserDetails, String> repository) {
        super(repository);
    }

    @Autowired
    private UserDetailsRepository repository;

    public UserDetails removeById(String id) {
        UserDetails entity = findById(id);
        entity.setIsDeleted(true);
        repository.save(entity);
        return entity;
    }

    public UserDetails findById(String id) {
        UserDetails entity =  repository.findById(id).orElse(null);
        if (entity == null)
            throw new EntityNotFoundException();
        if (entity.getIsDeleted())
            throw new EntityNotFoundException();
        return entity;
    }

    public List<UserDetails> findAll() {
        return repository.findAllNotDeleted();
    }
}
