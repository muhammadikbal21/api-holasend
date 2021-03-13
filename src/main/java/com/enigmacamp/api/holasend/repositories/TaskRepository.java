package com.enigmacamp.api.holasend.repositories;

import com.enigmacamp.api.holasend.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
}