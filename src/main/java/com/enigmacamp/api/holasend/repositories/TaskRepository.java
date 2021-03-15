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
            "WHERE status = 0",
            nativeQuery = true)
    List<Task> findAllWaitingTask();

    @Query(value = "SELECT * FROM task " +
            "WHERE courier = :courierId " +
            "AND (status = 1 OR status = 2)",
            nativeQuery = true)
    List<Task> findAllCourierUnfinishedTask(
            @Param("courierId") String courierId
    );

    @Query(value = "SELECT * FROM task " +
            "WHERE courier = :courierId " +
            "AND status = 3",
            nativeQuery = true)
    List<Task> findAllCourierTaskHistory(
            @Param("courierId") String courierId
    );
}