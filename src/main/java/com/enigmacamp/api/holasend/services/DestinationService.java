package com.enigmacamp.api.holasend.services;

import com.enigmacamp.api.holasend.entities.Destination;

import java.util.List;

public interface DestinationService extends CommonService<Destination, String> {

    public Destination removeById(String id);

    public Destination findById(String id);

    public List<Destination> findAll();

    Destination findByName(String name);
}
