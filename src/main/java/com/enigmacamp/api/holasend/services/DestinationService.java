package com.enigmacamp.api.holasend.services;

import com.enigmacamp.api.holasend.entities.Destination;

public interface DestinationService extends CommonService<Destination, String> {
    Destination findByName(String name);
}
