package com.enigmacamp.api.holasend.controller;

import com.enigmacamp.api.holasend.configs.jwt.JwtToken;
import com.enigmacamp.api.holasend.entities.CourierActivity;
import com.enigmacamp.api.holasend.entities.Task;
import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.models.ResponseMessage;
import com.enigmacamp.api.holasend.models.entitymodels.CourierActivityWithListOfTaskModel;
import com.enigmacamp.api.holasend.models.entitymodels.elements.CourierActivityElement;
import com.enigmacamp.api.holasend.models.entitymodels.response.CourierActivityResponse;
import com.enigmacamp.api.holasend.models.entitymodels.response.TaskResponse;
import com.enigmacamp.api.holasend.models.entitysearch.CourierActivitySearch;
import com.enigmacamp.api.holasend.models.pagination.PagedList;
import com.enigmacamp.api.holasend.services.CourierActivityService;
import com.enigmacamp.api.holasend.services.TaskService;
import com.enigmacamp.api.holasend.services.UserService;
import com.enigmacamp.api.holasend.utils.TokenToUserConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private void validateAdmin(HttpServletRequest request) {
        validateRoleAdmin(request, jwtTokenUtil, userService);
    }

    private void validateCourier(HttpServletRequest request) {
        validateRoleCourier(request, jwtTokenUtil, userService);
    }

    private void validateMinimumCourier(HttpServletRequest request) {
        validateRoleMinimumCourier(request, jwtTokenUtil, userService);
    }

    private User findUser(HttpServletRequest request) {
        return tokenUtil.convertToken(request, userService, jwtTokenUtil);
    }

    @PostMapping
    public ResponseMessage<CourierActivityWithListOfTaskModel> startActivity(
            HttpServletRequest request
    ) {
        validateCourier(request);
        User courier = findUser(request);

        List<Task> taskList = taskService.findAllUnfinishedTaskByCourierId(courier.getId());
        LocalDateTime time = LocalDateTime.now();

        CourierActivity activity = new CourierActivity();
        activity.setCourier(courier);
        activity.setLeavingTime(time);
        activity.setDate(LocalDate.now());

        CourierActivity finalActivity = service.save(activity);

        taskList.forEach(
                task -> {
                    task.setPickUpTime(time);
                    task.setCourier(courier);
                    task.setStatus(PICKUP);
                    task.setCourierActivity(finalActivity);
                    task = taskService.save(task);
                }
        );

        List<TaskResponse> taskResponses = taskList.stream()
                .map(e -> modelMapper.map(e, TaskResponse.class))
                .collect(Collectors.toList());

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

    @GetMapping("/{id}")
    public ResponseMessage<CourierActivityResponse> findTaskByCourierActivityId(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        validateMinimumCourier(request);
        CourierActivity activity = service.findById(id);
        CourierActivityResponse response =
                modelMapper.map(activity, CourierActivityResponse.class);

        return ResponseMessage.success(response);
    }

    @GetMapping("courier/{id}")
    public ResponseMessage<List<CourierActivityResponse>> findTaskByCourierId(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        validateMinimumCourier(request);
        List<CourierActivity> activity = service.findAllActivityByCourierId(id);


        List<CourierActivityResponse> response = activity.stream()
                .map(e -> modelMapper.map(e, CourierActivityResponse.class))
                .collect(Collectors.toList());

        return ResponseMessage.success(response);
    }


    @GetMapping("/all")
    public ResponseMessage<List<CourierActivityResponse>> findAll(
            HttpServletRequest request
    ) {
        validateAdmin(request);
        List<CourierActivity> entities = service.findAll();
        List<CourierActivityResponse> responses = entities.stream()
                .map(e -> modelMapper.map(e, CourierActivityResponse.class))
                .collect(Collectors.toList());

        return ResponseMessage.success(responses);
    }

    @GetMapping
    public ResponseMessage<PagedList<CourierActivityElement>> findAll(
            CourierActivitySearch model,
            HttpServletRequest request
    ) {
        validateAdmin(request);
        CourierActivity search = modelMapper.map(model, CourierActivity.class);

        Page<CourierActivity> entityPage = service.findAll(
                search, model.getPage(), model.getSize(), model.getSort()
        );
        List<CourierActivity> entities = entityPage.toList();

        List<CourierActivityElement> response = entities.stream()
                .map(e -> modelMapper.map(e, CourierActivityElement.class))
                .collect(Collectors.toList());

        PagedList<CourierActivityElement> data = new PagedList<>(
                response,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements()
        );

        return ResponseMessage.success(data);
    }
}
