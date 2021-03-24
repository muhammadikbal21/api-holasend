package com.enigmacamp.api.holasend.repositories;

import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    String table = "SELECT * FROM user WHERE is_deleted = 0 ";

    Boolean existsByUsername(String username);
    User findByUsername(String username);

    @Query(value = table, nativeQuery = true)
    List<User> findAllNotDeleted();

    @Query(value = table +
            "AND (role = 1 OR role = 0)",
            nativeQuery = true)
    List<User> findOnlyStaffOrAdmin();

    @Query(countQuery = "SELECT count(*) FROM user " +
            "WHERE is_deleted = :isDeleted " +
            "AND role = :role",
            nativeQuery = true)
    Long countByIsDeletedAndRole(
            @Param("isDeleted") Boolean isDeleted,
            @Param("role") RoleEnum role
    );
}