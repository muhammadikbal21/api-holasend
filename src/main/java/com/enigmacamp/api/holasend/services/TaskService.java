package com.enigmacamp.api.holasend.services;

import com.enigmacamp.api.holasend.entities.Task;

import java.util.List;

public interface TaskService extends CommonService<Task, String> {
    List<Task> findAllWaitingTask(Integer statusEnumIndex);
}
