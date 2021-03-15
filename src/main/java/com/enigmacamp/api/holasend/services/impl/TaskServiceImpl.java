package com.enigmacamp.api.holasend.services.impl;

import com.enigmacamp.api.holasend.entities.Task;
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
}