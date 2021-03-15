package com.enigmacamp.api.holasend.services;

import com.enigmacamp.api.holasend.entities.Task;

import java.util.List;

public interface TaskService extends CommonService<Task, String> {
    List<Task> findAllWaitingTask();
    List<Task> findAllCourierUnfinishedTask(String courierId);
    List<Task> findAllCourierTaskHistory(String courierId);
}
