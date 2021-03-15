package com.enigmacamp.api.holasend.repositories;

import com.enigmacamp.api.holasend.entities.CourierActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourierActivityRepository extends JpaRepository<CourierActivity, String> {

    @Query(value = "SELECT * FROM courier_activity " +
            "WHERE courier = :courierId",
            nativeQuery = true)
    List<CourierActivity> findAllActivityByCourierId (
            @Param("courierId") String courierId
    );
}