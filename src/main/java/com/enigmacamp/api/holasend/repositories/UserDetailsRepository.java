package com.enigmacamp.api.holasend.repositories;

import com.enigmacamp.api.holasend.entities.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, String> {
}