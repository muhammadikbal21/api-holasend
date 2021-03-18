package com.enigmacamp.api.holasend.services;

import com.enigmacamp.api.holasend.entities.Task;
import com.enigmacamp.api.holasend.enums.TaskStatusEnum;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskService extends CommonService<Task, String> {

    Task removeById(String id);

    Task findById(String id);

    List<Task> findAll();

    List<Task> findAllUnfinishedTaskByCourierId(String courierId);
    List<Task> findAllWaitingTask();
    List<Task> findAllCourierUnfinishedTask(String courierId);
    List<Task> findAllCourierTaskHistory(String courierId);
    List<Task> findAllUnfinishedRequestTask(String userId);
    List<Task> findAllFinishedRequestTask(String userId);
    List<Task> findAllPickedUpTaskByCourierActivityId(String activityId);
    Long countWaitingTask();
    Long countByCourier(String courierId, TaskStatusEnum status);
}
