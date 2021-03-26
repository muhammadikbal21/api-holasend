package com.enigmacamp.api.holasend.services.impl;

import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.enums.RoleEnum;
import com.enigmacamp.api.holasend.exceptions.EntityNotFoundException;
import com.enigmacamp.api.holasend.exceptions.InvalidCredentialsException;
import com.enigmacamp.api.holasend.repositories.UserRepository;
import com.enigmacamp.api.holasend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends CommonServiceImpl<User, String> implements UserService {
    protected UserServiceImpl(JpaRepository<User, String> repository) {
        super(repository);
    }
    
    @Autowired
    private UserRepository repository;

    public User removeById(String id) {
        User entity = findById(id);
        entity.setIsDeleted(true);
        repository.save(entity);
        return entity;
    }

    public User findById(String id) {
        User entity =  repository.findById(id).orElse(null);
        if (entity == null)
            throw new EntityNotFoundException();
        if (entity.getIsDeleted())
            throw new EntityNotFoundException();
        return entity;
    }

    public List<User> findAll() {
        return repository.findAllNotDeleted();
    }

    @Override
    public List<User> findOnlyStaffOrAdmin() {
        return repository.findOnlyStaffOrAdmin();
    }


    @Override
    public User findByUsername(String username) {
        User data = repository.findByUsername(username);
        if (data == null)
            throw new EntityNotFoundException();
        if (data.getIsDeleted())
            throw new InvalidCredentialsException();
        return data;
    }

    @Override
    public Boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public Long countByRole(RoleEnum role) {
        return repository.countByIsDeletedAndRole(false, role);
    }
}
