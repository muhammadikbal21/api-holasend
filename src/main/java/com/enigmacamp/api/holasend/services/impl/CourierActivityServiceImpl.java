package com.enigmacamp.api.holasend.services.impl;

import com.enigmacamp.api.holasend.entities.CourierActivity;
import com.enigmacamp.api.holasend.exceptions.NoActiveActivityException;
import com.enigmacamp.api.holasend.repositories.CourierActivityRepository;
import com.enigmacamp.api.holasend.services.CourierActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourierActivityServiceImpl extends CommonServiceImpl<CourierActivity, String>implements CourierActivityService {
    protected CourierActivityServiceImpl(JpaRepository<CourierActivity, String> repository) {
        super(repository);
    }

    @Autowired
    private CourierActivityRepository repository;

    @Override
    public List<CourierActivity> findAllActivityByCourierId(String courierId) {
        return repository.findAllActivityByCourierId(courierId);
    }

    @Override
    public CourierActivity findActiveCourierActivityByCourierId(String courierId) {
        CourierActivity result = repository.findActiveCourierActivityByCourierId(courierId);
        if (result == null)
            throw new NoActiveActivityException();
        return result;
    }
}
