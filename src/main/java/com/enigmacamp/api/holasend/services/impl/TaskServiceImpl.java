package com.enigmacamp.api.holasend.services.impl;

import com.enigmacamp.api.holasend.entities.Task;
import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.enums.PriorityEnum;
import com.enigmacamp.api.holasend.enums.TaskStatusEnum;
import com.enigmacamp.api.holasend.exceptions.EntityNotFoundException;
import com.enigmacamp.api.holasend.exceptions.UnidentifiedEnumException;
import com.enigmacamp.api.holasend.models.entitysearch.MyRequestTaskSearch;
import com.enigmacamp.api.holasend.models.entitysearch.TaskSearch;
import com.enigmacamp.api.holasend.models.pagination.PageSearch;
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
    public List<Task> findAllCourierTaskHistory(String courierId, PageSearch search) {
        return repository.findAllCourierTaskHistory(courierId, search.getSize(), (search.getSize())*search.getPage());
    }

    @Override
    public Long countAllCourierTaskHistory(User user) {
        return repository.countByIsDeletedAndCourierAndStatus(false, user, TaskStatusEnum.DELIVERED);
    }

    @Override
    public List<Task> findAllUnfinishedRequestTask(String userId) {
        return repository.findAllUnfinishedRequestTask(userId);
    }

    @Override
    public List<Task> findAllFinishedRequestTask(String userId, MyRequestTaskSearch search) {

        String status = "3";
        String priority = "";

        if (search.getPriority() != null) {
            String priorityUppercase = search.getPriority().toUpperCase();
            try {
                priority = String.valueOf(PriorityEnum.valueOf(priorityUppercase).ordinal());
            } catch (Exception e) {
                throw new UnidentifiedEnumException();
            }
        }

        return repository.findByPaginate(
                status,
                search.getDestinationId(),
                userId,
                priority,
                search.getAfter(),
                search.getBefore(),
                search.getSize(),
                search.getPage()
        );
    }

    @Override
    public Long countAllFinishedRequestTask(User user, MyRequestTaskSearch search) {

        String status = "3";
        String priority = "";

        if (search.getPriority() != null) {
            String priorityUppercase = search.getPriority().toUpperCase();
            try {
                priority = String.valueOf(PriorityEnum.valueOf(priorityUppercase).ordinal());
            } catch (Exception e) {
                throw new UnidentifiedEnumException();
            }
        }

        return repository.countPaginate(
                status,
                search.getDestinationId(),
                user.getId(),
                priority,
                search.getAfter(),
                search.getBefore()
        );
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
            try {
                status = String.valueOf(TaskStatusEnum.valueOf(statusUppercase).ordinal());
            } catch (Exception e) {
                throw new UnidentifiedEnumException();
            }
        }
        if (search.getPriority() != null) {
            String priorityUppercase = search.getPriority().toUpperCase();
            try {
                priority = String.valueOf(PriorityEnum.valueOf(priorityUppercase).ordinal());
            } catch (Exception e) {
                throw new UnidentifiedEnumException();
            }
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
    public List<Task> findByLastCreatedTask() {
        return repository.findByLastCreatedTask();
    }

    @Override
    public List<Task> findByLastPickedUpTask() {
        return repository.findByLastPickedUpTask();
    }

    @Override
    public List<Task> findByLastDeliveredTask() {
        return repository.findByLastDeliveredTask();
    }

    @Override
    public Long countPagination(TaskSearch search) {

        String status = "";
        String priority = "";

        if (search.getStatus() != null) {
            String statusUppercase = search.getStatus().toUpperCase();
            try {
                status = String.valueOf(TaskStatusEnum.valueOf(statusUppercase).ordinal());
            } catch (Exception e) {
                throw new UnidentifiedEnumException();
            }
        }
        if (search.getPriority() != null) {
            String priorityUppercase = search.getPriority().toUpperCase();
            try {
                priority = String.valueOf(PriorityEnum.valueOf(priorityUppercase).ordinal());
            } catch (Exception e) {
                throw new UnidentifiedEnumException();
            }
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
        return repository.countByIsDeletedAndStatus(false, TaskStatusEnum.WAITING);
    }

    @Override
    public Long countByCourier(String courierId, TaskStatusEnum status) {
        return repository.countByIsDeletedAndCourierIdAndStatus(false, courierId, status);
    }

    @Override
    public Long countByStatus(TaskStatusEnum status) {
        return repository.countByIsDeletedAndStatus(false, status);
    }
}