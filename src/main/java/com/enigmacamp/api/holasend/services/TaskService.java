package com.enigmacamp.api.holasend.services;

import com.enigmacamp.api.holasend.entities.Task;
import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.enums.TaskStatusEnum;
import com.enigmacamp.api.holasend.models.entitysearch.MyRequestTaskSearch;
import com.enigmacamp.api.holasend.models.entitysearch.TaskSearch;
import com.enigmacamp.api.holasend.models.pagination.PageSearch;

import java.util.List;

public interface TaskService extends CommonService<Task, String> {

    Task removeById(String id);

    Task findById(String id);

    List<Task> findAll();

    List<Task> findAllUnfinishedTaskByCourierId(String courierId);
    List<Task> findAllWaitingTask();
    List<Task> findAllCourierUnfinishedTask(String courierId);
    List<Task> findAllCourierTaskHistory(String courierId, PageSearch search);
    Long countAllCourierTaskHistory(User user);
    List<Task> findAllUnfinishedRequestTask(String userId);
    List<Task> findAllFinishedRequestTask(String userId, MyRequestTaskSearch search);
    Long countAllFinishedRequestTask(User user, MyRequestTaskSearch search);
    List<Task> findAllPickedUpTaskByCourierActivityId(String activityId);
    List<Task> findByRange(String after, String before);
    List<Task> findTasksByCreateDateOrStatusOrDestinationOrRequestByOrPriority(TaskSearch search);
    List<Task> findByLastCreatedTask();
    List<Task> findByLastPickedUpTask();
    List<Task> findByLastDeliveredTask();

    Long countPagination(TaskSearch search);
    Long countWaitingTask();
    Long countByCourier(String courierId, TaskStatusEnum status);
    Long countByStatus(TaskStatusEnum status);
}
