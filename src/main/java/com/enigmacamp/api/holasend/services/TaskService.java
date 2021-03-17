package com.enigmacamp.api.holasend.services;

import com.enigmacamp.api.holasend.entities.Task;

import java.util.List;

public interface TaskService extends CommonService<Task, String> {

    public Task removeById(String id);

    public Task findById(String id);

    public List<Task> findAll();

    List<Task> findAllUnfinishedTaskByCourierId(String courierId);
    List<Task> findAllWaitingTask();
    List<Task> findAllCourierUnfinishedTask(String courierId);
    List<Task> findAllCourierTaskHistory(String courierId);
    List<Task> findAllUnfinishedRequestTask(String userId);
    List<Task> findAllFinishedRequestTask(String userId);
    List<Task> findAllPickedUpTaskByCourierActivityId(String activityId);
}
