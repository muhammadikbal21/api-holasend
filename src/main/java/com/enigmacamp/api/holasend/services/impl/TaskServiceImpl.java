package com.enigmacamp.api.holasend.services.impl;

import com.enigmacamp.api.holasend.entities.Task;
import com.enigmacamp.api.holasend.services.TaskService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl extends CommonServiceImpl<Task, String>implements TaskService {
    protected TaskServiceImpl(JpaRepository<Task, String> repository) {
        super(repository);
    }
}
