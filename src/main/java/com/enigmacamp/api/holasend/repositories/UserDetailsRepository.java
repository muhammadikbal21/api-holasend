package com.enigmacamp.api.holasend.repositories;

import com.enigmacamp.api.holasend.entities.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, String> {
    String table = "SELECT * FROM user_details WHERE is_deleted = 0 ";

    @Query(value = table, nativeQuery = true)
    List<UserDetails> findAllNotDeleted();

}