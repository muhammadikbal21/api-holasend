package com.enigmacamp.api.holasend.services;

import com.enigmacamp.api.holasend.entities.CourierActivity;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourierActivityService extends CommonService<CourierActivity, String> {
    List<CourierActivity> findAllActivityByCourierId (String courierId);
}
