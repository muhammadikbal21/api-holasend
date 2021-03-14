package com.enigmacamp.api.holasend.services.impl;

import com.enigmacamp.api.holasend.exceptions.EntityNotFoundException;
import com.enigmacamp.api.holasend.services.CommonService;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class CommonServiceImpl<T, ID> implements CommonService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    protected CommonServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public T removeById(ID id) {
        T entity = findById(id);
        repository.deleteById(id);
        return entity;
    }

    @Override
    public T findById(ID id) {
        T entity =  repository.findById(id).orElse(null);
        if (entity == null)
            throw new EntityNotFoundException();
        return entity;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<T> findAll(T search, int page, int size, Sort.Direction direction) {
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
