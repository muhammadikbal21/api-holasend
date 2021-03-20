package com.enigmacamp.api.holasend.repositories;

import com.enigmacamp.api.holasend.entities.Task;
import com.enigmacamp.api.holasend.enums.PriorityEnum;
import com.enigmacamp.api.holasend.enums.TaskStatusEnum;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    String table = "SELECT * FROM task WHERE is_deleted = 0 ";

    @Query(value = table +
            "AND courier = :courierId " +
            "AND delivered_time IS NULL",
            nativeQuery = true)
    List<Task> findAllUnfinishedTaskByCourierId(
            @Param("courierId") String courierId
    );

    @Query(value = table +
            "AND status = 0 " +
            "ORDER BY priority",
            nativeQuery = true)
    List<Task> findAllWaitingTask();

    @Query(value = table +
            "AND courier = :courierId " +
            "AND (status = 1 OR status = 2)",
            nativeQuery = true)
    List<Task> findAllCourierUnfinishedTask(
            @Param("courierId") String courierId
    );

    @Query(value = table +
            "AND courier = :courierId " +
            "AND status = 3",
            nativeQuery = true)
    List<Task> findAllCourierTaskHistory(
            @Param("courierId") String courierId
    );

    @Query(value = table +
            "AND request_by = :userId " +
            "AND status != 3",
            nativeQuery = true)
    List<Task> findAllUnfinishedRequestTask(
            @Param("userId") String userId
    );

    @Query(value = table +
            "AND request_by = :userId " +
            "AND status = 3",
            nativeQuery = true)
    List<Task> findAllFinishedRequestTask(
            @Param("userId") String userId
    );

    @Query(value = table +
            "AND courier_activity_id " +
            "AND status = 2", nativeQuery = true)
    List<Task> findAllPickedUpTaskByCourierActivityId(
            @Param("activityId") String activityId);

    @Query(value = table, nativeQuery = true)
    List<Task> findAllNotDeleted();

    @Query(value = table +
            "AND created_date > :after " +
            "AND created_date < :before"
    , nativeQuery = true)
    List<Task> findByRange(
            @Param("after") String after,
            @Param("before") String before
    );

    @Query(value = table +
            "AND status LIKE %:status% " +
            "AND destination_id LIKE %:destination% " +
            "AND request_by LIKE %:requestBy% " +
            "AND priority LIKE %:priority% " +
            "AND created_date > :after " +
            "AND created_date < :before " +
            "ORDER BY created_date ASC " +
            "LIMIT :size " +
            "OFFSET :page ",
            nativeQuery = true)
    List<Task> findByPaginate(
            @Param("status") String status,
            @Param("destination") String destination,
            @Param("requestBy") String requestBy,
            @Param("priority") String priority,
            @Param("after") String after,
            @Param("before") String before,
            @Param("size") Long size,
            @Param("page") Long page
    );

    @Query(value = "SELECT count(*) FROM task " +
            "WHERE is_deleted = 0 " +
            "AND status LIKE %:status% " +
            "AND destination_id LIKE %:destination% " +
            "AND request_by LIKE %:requestBy% " +
            "AND priority LIKE %:priority% " +
            "AND created_date > :after " +
            "AND created_date < :before " ,
            nativeQuery = true)
    Long countPaginate(
            @Param("status") String status,
            @Param("destination") String destination,
            @Param("requestBy") String requestBy,
            @Param("priority") String priority,
            @Param("after") String after,
            @Param("before") String before
    );

    @Query(countQuery = "SELECT count(*) FROM task " +
            "WHERE is_deleted = 0 " +
            "AND status = :status",
            nativeQuery = true)
    Long countByStatus(
            @Param("status") TaskStatusEnum status);

    @Query(countQuery = "SELECT count(*) FROM task " +
            "WHERE is_deleted = 0 " +
            "AND status = :status " +
            "AND courier_id = :courierId "
    , nativeQuery = true)
    Long countByCourierIdAndStatus(
            @Param("courierId") String courierId, @Param("status") TaskStatusEnum status
    );
}