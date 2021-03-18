package com.enigmacamp.api.holasend.services;

import com.enigmacamp.api.holasend.entities.Destination;

import java.util.List;

public interface DestinationService extends CommonService<Destination, String> {

    Destination removeById(String id);

    Destination findById(String id);

    List<Destination> findAll();

    Destination findByName(String name);
}
