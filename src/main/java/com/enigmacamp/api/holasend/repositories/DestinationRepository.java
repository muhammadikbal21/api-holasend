package com.enigmacamp.api.holasend.repositories;

import com.enigmacamp.api.holasend.entities.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, String> {

    String table = "SELECT * FROM destination WHERE is_deleted = 0 ";

    Destination findByName(String name);

    @Query(value = table, nativeQuery = true)
    List<Destination> findAllNotDeleted();

}