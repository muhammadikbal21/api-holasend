package com.enigmacamp.api.holasend.services.impl;

import com.enigmacamp.api.holasend.entities.CourierActivity;
import com.enigmacamp.api.holasend.services.CourierActivityService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CourierActivityServiceImpl extends CommonServiceImpl<CourierActivity, String>implements CourierActivityService {
    protected CourierActivityServiceImpl(JpaRepository<CourierActivity, String> repository) {
        super(repository);
    }
}
