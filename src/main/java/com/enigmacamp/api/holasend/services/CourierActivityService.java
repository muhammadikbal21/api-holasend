package com.enigmacamp.api.holasend.services;

import com.enigmacamp.api.holasend.entities.CourierActivity;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourierActivityService extends CommonService<CourierActivity, String> {

    public CourierActivity removeById(String id);

    public CourierActivity findById(String id);

    public List<CourierActivity> findAll();

    List<CourierActivity> findAllActivityByCourierId (String courierId);
    CourierActivity findActiveCourierActivityByCourierId(String courierId);

}
