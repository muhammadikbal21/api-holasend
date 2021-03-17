package com.enigmacamp.api.holasend.repositories;

import com.enigmacamp.api.holasend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    String table = "SELECT * FROM user WHERE is_deleted = 0 ";

    Boolean existsByUsername(String username);
    User findByUsername(String username);

    @Query(value = table, nativeQuery = true)
    List<User> findAllNotDeleted();
}