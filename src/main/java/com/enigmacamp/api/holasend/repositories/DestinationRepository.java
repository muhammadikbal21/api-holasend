package com.enigmacamp.api.holasend.repositories;

import com.enigmacamp.api.holasend.entities.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, String> {
    Destination findByName(String name);
}