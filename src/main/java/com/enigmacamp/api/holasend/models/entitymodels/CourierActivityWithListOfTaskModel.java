package com.enigmacamp.api.holasend.models.entitymodels;

import com.enigmacamp.api.holasend.models.entitymodels.response.CourierActivityResponse;
import com.enigmacamp.api.holasend.models.entitymodels.response.TaskResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class CourierActivityWithListOfTaskModel {

    private CourierActivityResponse courierActivity;

    private List<TaskResponse> taskList;
}
