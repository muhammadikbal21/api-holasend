package com.enigmacamp.api.holasend.repositories;

import com.enigmacamp.api.holasend.entities.CourierActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourierActivityRepository extends JpaRepository<CourierActivity, String> {
    String table = "SELECT * FROM courier_activity WHERE is_deleted = 0 ";

    @Query(value = table +
            "AND courier = :courierId",
            nativeQuery = true)
    List<CourierActivity> findAllActivityByCourierId (
            @Param("courierId") String courierId
    );

    @Query(value = table +
            "AND courier_id = :courierId " +
            "AND return_time IS NULL " +
            "LIMIT 1",
            nativeQuery = true)
    CourierActivity findActiveCourierActivityByCourierId(String courierId);

    @Query(value = table, nativeQuery = true)
    List<CourierActivity> findAllNotDeleted();
}