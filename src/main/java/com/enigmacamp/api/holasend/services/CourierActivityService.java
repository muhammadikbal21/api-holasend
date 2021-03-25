package com.enigmacamp.api.holasend.services;

import com.enigmacamp.api.holasend.entities.CourierActivity;

import java.util.List;

public interface CourierActivityService extends CommonService<CourierActivity, String> {

    CourierActivity removeById(String id);

    CourierActivity findById(String id);

    List<CourierActivity> findAll();

    List<CourierActivity> findAllActivityByCourierId (String courierId);
    CourierActivity findActiveCourierActivityByCourierId(String courierId);

}
