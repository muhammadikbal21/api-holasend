package com.enigmacamp.api.holasend.controller;

import com.enigmacamp.api.holasend.configs.jwt.JwtToken;
import com.enigmacamp.api.holasend.entities.CourierActivity;
import com.enigmacamp.api.holasend.entities.Task;
import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.enums.TaskStatusEnum;
import com.enigmacamp.api.holasend.models.ResponseMessage;
import com.enigmacamp.api.holasend.models.entitymodels.CourierActivityWithListOfTaskModel;
import com.enigmacamp.api.holasend.models.entitymodels.ListOfIdModel;
import com.enigmacamp.api.holasend.models.entitymodels.response.CourierActivityResponse;
import com.enigmacamp.api.holasend.models.entitymodels.response.TaskResponse;
import com.enigmacamp.api.holasend.services.CourierActivityService;
import com.enigmacamp.api.holasend.services.TaskService;
import com.enigmacamp.api.holasend.services.UserService;
import com.enigmacamp.api.holasend.utils.TokenToUserConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.enigmacamp.api.holasend.controller.validations.RoleValidation.*;
import static com.enigmacamp.api.holasend.enums.TaskStatusEnum.PICKUP;

@RestController
@RequestMapping("/courier-activity")
public class CourierActivityController {

    @Autowired
    private CourierActivityService service;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtToken jwtTokenUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TokenToUserConverter tokenUtil;

    private void validateCourier(HttpServletRequest request) {
        validateRoleCourier(request, jwtTokenUtil, userService);
    }

    private User findUser(HttpServletRequest request) {
        return tokenUtil.convertToken(request, userService, jwtTokenUtil);
    }

    @PostMapping
    public ResponseMessage<CourierActivityWithListOfTaskModel> startActivity(
            @RequestBody ListOfIdModel listOfId,
            HttpServletRequest request
    ) {
        validateCourier(request);
        User courier = findUser(request);

        List<Task> taskList = new ArrayList<>();
        LocalDateTime time = LocalDateTime.now();

        listOfId.getId().forEach(
                id -> {
                    Task task = taskService.findById(id);
                    taskList.add(task);
                }
        );

        taskList.forEach(
                task -> {
                    task.setPickUpTime(time);
                    task.setCourier(courier);
                    task.setStatus(PICKUP);
                    task = taskService.save(task);
                }
        );

        List<TaskResponse> taskResponses = taskList.stream()
                .map(e -> modelMapper.map(e, TaskResponse.class))
                .collect(Collectors.toList());

        CourierActivity activity = new CourierActivity();
        activity.setCourier(courier);
        activity.setLeavingTime(time);
        activity = service.save(activity);

        CourierActivityResponse response =
                modelMapper.map(activity, CourierActivityResponse.class);

        CourierActivityWithListOfTaskModel data =
                new CourierActivityWithListOfTaskModel(response, taskResponses);

        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<CourierActivityResponse> stopActivity(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        validateCourier(request);
        CourierActivity activity = service.findById(id);
        activity.setReturnTime(LocalDateTime.now());
        activity = service.save(activity);

        CourierActivityResponse response =
                modelMapper.map(activity, CourierActivityResponse.class);

        return ResponseMessage.success(response);

    }
}
