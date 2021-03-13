package com.enigmacamp.api.holasend.services.impl;

import com.enigmacamp.api.holasend.entities.Destination;
import com.enigmacamp.api.holasend.services.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationServiceImpl implements DestinationService {

    @Autowired
    private JpaRepository<Destination, String> repository;

    @Override
    public Destination save(Destination entity) {
        return repository.save(entity);
    }

    @Override
    public Destination removeById(String id) {
        Destination entity = findById(id);
        if(entity != null) {
            repository.deleteById(id);
            return entity;
        } else {
            return null;
        }
    }

    @Override
    public Destination findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Destination> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Destination> findAll(Destination search, int page, int size, Sort.Direction direction) {
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
