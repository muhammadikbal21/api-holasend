package com.enigmacamp.api.holasend.services.impl;

import com.enigmacamp.api.holasend.entities.Task;
import com.enigmacamp.api.holasend.enums.PriorityEnum;
import com.enigmacamp.api.holasend.enums.TaskStatusEnum;
import com.enigmacamp.api.holasend.exceptions.EntityNotFoundException;
import com.enigmacamp.api.holasend.models.entitysearch.TaskSearch;
import com.enigmacamp.api.holasend.repositories.TaskRepository;
import com.enigmacamp.api.holasend.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl extends CommonServiceImpl<Task, String>implements TaskService {
    protected TaskServiceImpl(JpaRepository<Task, String> repository) {
        super(repository);
    }

    @Autowired
    private TaskRepository repository;


    public Task removeById(String id) {
        Task entity = findById(id);
        entity.setIsDeleted(true);
        repository.save(entity);
        return entity;
    }

    public Task findById(String id) {
        Task entity =  repository.findById(id).orElse(null);
        if (entity == null)
            throw new EntityNotFoundException();
        if (entity.getIsDeleted())
            throw new EntityNotFoundException();
        return entity;
    }

    public List<Task> findAll() {
        return repository.findAllNotDeleted();
    }

    @Override
    public List<Task> findAllUnfinishedTaskByCourierId(String courierId) {
        return repository.findAllUnfinishedTaskByCourierId(courierId);
    }

    @Override
    public List<Task> findAllWaitingTask() {
        return repository.findAllWaitingTask();
    }

    @Override
    public List<Task> findAllCourierUnfinishedTask(String courierId) {
        return repository.findAllCourierUnfinishedTask(courierId);
    }

    @Override
    public List<Task> findAllCourierTaskHistory(String courierId) {
        return repository.findAllCourierTaskHistory(courierId);
    }

    @Override
    public List<Task> findAllUnfinishedRequestTask(String userId) {
        return repository.findAllUnfinishedRequestTask(userId);
    }

    @Override
    public List<Task> findAllFinishedRequestTask(String userId) {
        return repository.findAllFinishedRequestTask(userId);
    }

    @Override
    public List<Task> findAllPickedUpTaskByCourierActivityId(String activityId) {
        return repository.findAllPickedUpTaskByCourierActivityId(activityId);
    }

    @Override
    public List<Task> findByRange(String after, String before) {
        return repository.findByRange(after, before);
    }

    @Override
    public List<Task> findTasksByCreateDateOrStatusOrDestinationOrRequestByOrPriority(TaskSearch search) {
        String status = "";
        String priority = "";

        if (search.getStatus() != null) {
            String statusUppercase = search.getStatus().toUpperCase();
            status = String.valueOf(TaskStatusEnum.valueOf(statusUppercase).ordinal());
        }
        if (search.getPriority() != null) {
            String priorityUppercase = search.getPriority().toUpperCase();
            priority = String.valueOf(PriorityEnum.valueOf(priorityUppercase).ordinal());
        }

        return repository.findByPaginate(
                status,
                search.getDestinationId(),
                search.getRequestById(),
                priority,
                search.getAfter(),
                search.getBefore(),
                search.getSize(),
                search.getPage()
        );
    }

    @Override
    public Long countTasksByCreateDateOrStatusOrDestinationOrRequestByOrPriority(TaskSearch search) {

        String status = "";
        String priority = "";

        if (search.getStatus() != null) {
            String statusUppercase = search.getStatus().toUpperCase();
            status = String.valueOf(TaskStatusEnum.valueOf(statusUppercase).ordinal());
        }
        if (search.getPriority() != null) {
            String priorityUppercase = search.getPriority().toUpperCase();
            priority = String.valueOf(PriorityEnum.valueOf(priorityUppercase).ordinal());
        }

        return repository.countPaginate(
                status,
                search.getDestinationId(),
                search.getRequestById(),
                priority,
                search.getAfter(),
                search.getBefore()
        );
    }

    @Override
    public Long countWaitingTask() {
        return repository.countByStatus(TaskStatusEnum.WAITING);
    }

    @Override
    public Long countByCourier(String courierId, TaskStatusEnum status) {
        return repository.countByCourierIdAndStatus(courierId, status);
    }
}