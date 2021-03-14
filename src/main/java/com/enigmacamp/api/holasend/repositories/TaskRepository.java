package com.enigmacamp.api.holasend.repositories;

import com.enigmacamp.api.holasend.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    @Query(value = "SELECT * FROM task " +
            "WHERE status = :statusEnum",
            nativeQuery = true)
    List<Task> findAllWaitingTask(
            @Param("statusEnum") Integer statusEnumIndex
    );

//    public List<Task> findAllCourierUnfinishedTask();
//
//    public List<Task> findAllCourierTaskHistory();
}