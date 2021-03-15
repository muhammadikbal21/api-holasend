package com.enigmacamp.api.holasend.services.impl;

import com.enigmacamp.api.holasend.entities.Destination;
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
        return repository.findByName(name);
    }
}
