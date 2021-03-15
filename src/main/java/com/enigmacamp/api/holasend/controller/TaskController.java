package com.enigmacamp.api.holasend.controller;

import com.enigmacamp.api.holasend.configs.jwt.JwtToken;
import com.enigmacamp.api.holasend.entities.Destination;
import com.enigmacamp.api.holasend.entities.Task;
import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.exceptions.EntityNotFoundException;
import com.enigmacamp.api.holasend.exceptions.InvalidPermissionsException;
import com.enigmacamp.api.holasend.models.ResponseMessage;
import com.enigmacamp.api.holasend.models.entitymodels.request.TaskRequest;
import com.enigmacamp.api.holasend.models.entitymodels.response.TaskResponse;
import com.enigmacamp.api.holasend.models.entitysearch.TaskSearch;
import com.enigmacamp.api.holasend.models.pagination.PagedList;
import com.enigmacamp.api.holasend.services.DestinationService;
import com.enigmacamp.api.holasend.services.TaskService;
import com.enigmacamp.api.holasend.services.UserService;
import com.enigmacamp.api.holasend.utils.TokenToUserConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.enigmacamp.api.holasend.controller.validations.RoleValidation.*;
import static com.enigmacamp.api.holasend.enums.TaskStatusEnum.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService service;

    @Autowired
    private DestinationService destinationService;

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

    private void validateAdminOrStaff(HttpServletRequest request) {
        validateRoleAdminOrStaff(request, jwtTokenUtil, userService);
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
    public ResponseMessage<TaskResponse> add(
            @RequestBody @Valid TaskRequest model,
            HttpServletRequest request
    ) {
        validateAdminOrStaff(request);
        Task entity = modelMapper.map(model, Task.class);
        Destination destination = destinationService.findById(model.getDestinationId());
        if (destination == null)
            throw new EntityNotFoundException();

        entity.setDestination(destination);

        User user = findUser(request);
        entity.setRequestBy(user);
        entity.setStatus(WAITING);
        entity = service.save(entity);

        TaskResponse data = modelMapper.map(entity, TaskResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/assign/cancel/{id}")
    public ResponseMessage<TaskResponse> cancelAssignTask(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        validateCourier(request);
        User courier = findUser(request);

        Task entity = service.findById(id);
        if (!entity.getCourier().equals(courier))
            throw new InvalidPermissionsException();

        entity.setStatus(WAITING);
        service.save(entity);

        TaskResponse data = modelMapper.map(entity, TaskResponse.class);

        return ResponseMessage.success(data);
    }

    @PutMapping("/assign/{id}")
    public ResponseMessage<TaskResponse> assignTask(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        validateCourier(request);
        User courier = findUser(request);

        Task entity = service.findById(id);
        entity.setCourier(courier);
        entity.setStatus(ASSIGNED);
        service.save(entity);

        TaskResponse data = modelMapper.map(entity, TaskResponse.class);

        return ResponseMessage.success(data);
    }

    @PutMapping("/finish/{id}")
    public ResponseMessage<TaskResponse> finishTask(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        validateCourier(request);

        Task entity = service.findById(id);
        entity.setStatus(DELIVERED);
        entity.setDeliveredTime(LocalDateTime.now());
        service.save(entity);

        TaskResponse data = modelMapper.map(entity, TaskResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/all")
    public ResponseMessage<List<TaskResponse>> findAll(
            HttpServletRequest request
    ) {
        validateAdmin(request);
        List<Task> entities = service.findAll();
        List<TaskResponse> responses = entities.stream()
                .map(e -> modelMapper.map(e, TaskResponse.class))
                .collect(Collectors.toList());

        return ResponseMessage.success(responses);
    }

    @GetMapping
    public ResponseMessage<PagedList<TaskResponse>> findAll(
            TaskSearch model,
            HttpServletRequest request
    ) {
        validateAdmin(request);
        Task search = modelMapper.map(model, Task.class);

        Page<Task> entityPage = service.findAll(
                search, model.getPage(), model.getSize(), model.getSort()
        );
        List<Task> entities = entityPage.toList();

        List<TaskResponse> responses = entities.stream()
                .map(e -> modelMapper.map(e, TaskResponse.class))
                .collect(Collectors.toList());

        PagedList<TaskResponse> data = new PagedList<>(
                responses,
                entityPage.getNumber(),
                entities.size(),
                entityPage.getTotalElements()
        );

        return ResponseMessage.success(data);
    }

    @GetMapping("/waiting")
    public ResponseMessage<List<TaskResponse>> findAllWaitingTask(
            HttpServletRequest request
    ) {
        validateMinimumCourier(request);
        List<Task> entities = service.findAllWaitingTask();
        List<TaskResponse> responses = entities.stream()
                .map(e -> modelMapper.map(e, TaskResponse.class))
                .collect(Collectors.toList());

        return ResponseMessage.success(responses);
    }

    @GetMapping("/my-task/unfinished")
    public ResponseMessage<List<TaskResponse>> findAllUnfinishedTask(
            HttpServletRequest request
    ) {
        validateCourier(request);
        User courier = findUser(request);
        List<Task> entities = service.findAllCourierUnfinishedTask(courier.getId());
        List<TaskResponse> responses = entities.stream()
                .map(e -> modelMapper.map(e, TaskResponse.class))
                .collect(Collectors.toList());

        return ResponseMessage.success(responses);
    }

    @GetMapping("/my-task/finished")
    public ResponseMessage<List<TaskResponse>> findAllFinishedTask(
            HttpServletRequest request
    ) {
        validateCourier(request);
        User courier = findUser(request);
        List<Task> entities = service.findAllCourierTaskHistory(courier.getId());
        List<TaskResponse> responses = entities.stream()
                .map(e -> modelMapper.map(e, TaskResponse.class))
                .collect(Collectors.toList());

        return ResponseMessage.success(responses);
    }

    @GetMapping("/my-request/unfinished")
    public ResponseMessage<List<TaskResponse>> findAllUnfinishedRequestedTask(
            HttpServletRequest request
    ) {
        validateAdminOrStaff(request);
        User user = findUser(request);
        List<Task> entities = service.findAllUnfinishedRequestTask(user.getId());
        List<TaskResponse> responses = entities.stream()
                .map(e -> modelMapper.map(e, TaskResponse.class))
                .collect(Collectors.toList());

        return ResponseMessage.success(responses);
    }

    @GetMapping("/my-request/finished")
    public ResponseMessage<List<TaskResponse>> findAllFinishedRequestedTask(
            HttpServletRequest request
    ) {
        validateAdminOrStaff(request);
        User user = findUser(request);
        List<Task> entities = service.findAllFinishedRequestTask(user.getId());
        List<TaskResponse> responses = entities.stream()
                .map(e -> modelMapper.map(e, TaskResponse.class))
                .collect(Collectors.toList());

        return ResponseMessage.success(responses);
    }
}
