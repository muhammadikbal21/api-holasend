package com.enigmacamp.api.holasend.repositories;

import com.enigmacamp.api.holasend.entities.CourierActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierActivityRepository extends JpaRepository<CourierActivity, String> {
}