package com.enigmacamp.api.holasend.services.impl;

import com.enigmacamp.api.holasend.entities.Destination;
import com.enigmacamp.api.holasend.exceptions.EntityNotFoundException;
import com.enigmacamp.api.holasend.repositories.DestinationRepository;
import com.enigmacamp.api.holasend.services.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationServiceImpl extends CommonServiceImpl<Destination, String> implements DestinationService {

    protected DestinationServiceImpl(JpaRepository<Destination, String> repository) {
        super(repository);
    }

    @Autowired
    DestinationRepository repository;

    @Override
    public Destination findByName(String name) {
        Destination data = repository.findByName(name);
        System.out.println("DATA" + data);
        if (data != null)
            if (data.getIsDeleted())
                return null;
        return data;
    }


    public Destination removeById(String id) {
        Destination entity = findById(id);
        entity.setIsDeleted(true);
        repository.save(entity);
        return entity;
    }

    public Destination findById(String id) {
        Destination entity =  repository.findById(id).orElse(null);
        if (entity == null)
            throw new EntityNotFoundException();
        if (entity.getIsDeleted())
            throw new EntityNotFoundException();
        return entity;
    }

    public List<Destination> findAll() {
        return repository.findAllNotDeleted();
    }
}
