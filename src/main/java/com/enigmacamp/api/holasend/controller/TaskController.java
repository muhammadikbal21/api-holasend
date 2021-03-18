package com.enigmacamp.api.holasend.controller;

import com.enigmacamp.api.holasend.configs.exporter.ReportModelMapper;
import com.enigmacamp.api.holasend.configs.exporter.TaskReportExporter;
import com.enigmacamp.api.holasend.configs.jwt.JwtToken;
import com.enigmacamp.api.holasend.entities.CourierActivity;
import com.enigmacamp.api.holasend.entities.Destination;
import com.enigmacamp.api.holasend.entities.Task;
import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.exceptions.*;
import com.enigmacamp.api.holasend.models.CountModel;
import com.enigmacamp.api.holasend.models.ResponseMessage;
import com.enigmacamp.api.holasend.models.entitymodels.elements.TaskElement;
import com.enigmacamp.api.holasend.models.entitymodels.request.DateRangeRequest;
import com.enigmacamp.api.holasend.models.entitymodels.request.TaskRequest;
import com.enigmacamp.api.holasend.models.entitymodels.response.TaskResponse;
import com.enigmacamp.api.holasend.models.entitysearch.TaskSearch;
import com.enigmacamp.api.holasend.models.excel.TaskReportModel;
import com.enigmacamp.api.holasend.models.pagination.PagedList;
import com.enigmacamp.api.holasend.models.validations.DateTimeValidator;
import com.enigmacamp.api.holasend.services.CourierActivityService;
import com.enigmacamp.api.holasend.services.DestinationService;
import com.enigmacamp.api.holasend.services.TaskService;
import com.enigmacamp.api.holasend.services.UserService;
import com.enigmacamp.api.holasend.utils.TokenToUserConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private CourierActivityService courierActivityService;

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
        if (entity.getStatus().equals(DELIVERED) || entity.getStatus().equals(PICKUP))
            throw new TaskDeliveredException();

        if (!entity.getCourier().equals(courier))
            throw new InvalidPermissionsException();
        entity.setCourier(null);
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

        CourierActivity activity =
                courierActivityService.findActiveCourierActivityByCourierId(courier.getId());

        if (activity != null) {
            throw new ActiveActivityException();
        }

        Task entity = service.findById(id);
        if (entity.getCourier() != null)
            throw new TaskHasTakenException();

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
        if (entity.getPickUpTime() == null)
            throw new TaskDidntStartedException();

        if (entity.getStatus().equals(DELIVERED))
            throw new TaskDeliveredException();

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
    public ResponseMessage<PagedList<TaskElement>> findAll(
            TaskSearch model,
            HttpServletRequest request
    ) {
        validateAdmin(request);
        Task search = modelMapper.map(model, Task.class);

        search.setPickUpTime(model.getPickUpTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        search.setDeliveredTime(model.getDeliveredTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        Page<Task> entityPage = service.findAll(
                search, model.getPage(), model.getSize(), model.getSort()
        );
        List<Task> entities = entityPage.toList();

        List<TaskElement> responses = entities.stream()
                .map(e -> modelMapper.map(e, TaskElement.class))
                .collect(Collectors.toList());

        PagedList<TaskElement> data = new PagedList<>(
                responses,
                entityPage.getNumber(),
                entities.size(),
                entityPage.getTotalElements()
        );

        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<TaskResponse> removeById(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        validateAdmin(request);
        Task entity = service.removeById(id);

        TaskResponse data = modelMapper.map(entity, TaskResponse.class);
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

    @GetMapping("/{id}")
    public ResponseMessage<TaskResponse> findById(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        validateMinimumCourier(request);
        Task entity = service.findById(id);

        TaskResponse data = modelMapper.map(entity, TaskResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/export")
    public void exportToExcel(
            HttpServletResponse response
    ) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Report.xlsx";

        response.setHeader(headerKey, headerValue);
        List<Task> taskList = service.findAll();

        List<TaskReportModel> modelList = ReportModelMapper.convert(taskList);

        TaskReportExporter exp = new TaskReportExporter(modelList);
        exp.export(response);
    }

    @GetMapping("/count/waiting")
    public ResponseMessage<Long> countWaiting(
            HttpServletRequest request
    ) {
        validateMinimumCourier(request);

        Long count = service.countWaitingTask();

        return ResponseMessage.success(count);
    }


    @GetMapping("/count/assigned")
    public ResponseMessage<Long> countByAssignedToCourier(
            HttpServletRequest request
    ) {
        validateCourier(request);

        User courier = findUser(request);

        Long count = service.countByCourier(courier.getId(), ASSIGNED);

        return ResponseMessage.success(count);
    }

    @GetMapping("/count/pick-up")
    public ResponseMessage<Long> countByPickedUpByCourier(
            HttpServletRequest request
    ) {
        validateCourier(request);

        User courier = findUser(request);

        Long count = service.countByCourier(courier.getId(), PICKUP);

        return ResponseMessage.success(count);
    }

    @GetMapping("/count/delivered")
    public ResponseMessage<Long> countByDelivered(
            HttpServletRequest request
    ) {
        validateCourier(request);

        User courier = findUser(request);

        Long count = service.countByCourier(courier.getId(), DELIVERED);

        return ResponseMessage.success(count);
    }

    @GetMapping("/count")
    public ResponseMessage<CountModel> count(
            HttpServletRequest request
    ) {
        validateCourier(request);

        User courier = findUser(request);

        CountModel count = new CountModel(
                service.countWaitingTask(),
                service.countByCourier(courier.getId(), ASSIGNED),
                service.countByCourier(courier.getId(), PICKUP),
                service.countByCourier(courier.getId(), DELIVERED)
        );

        return ResponseMessage.success(count);
    }

    @GetMapping("/range")
    public ResponseMessage<List<TaskResponse>> findByRange(
            @Valid DateRangeRequest model,
            HttpServletRequest request
    ) {
        validateAdmin(request);

        Boolean validDateStart = DateTimeValidator.validate(model.getStart());
        Boolean validDateEnd = DateTimeValidator.validate(model.getEnd());

        if (!validDateStart || !validDateEnd)
            throw new DateInvalidException();

        List<Task> taskList = service.findByRange(
                model.getStart(),
                model.getEnd()
        );

        List<TaskResponse> data = taskList.stream().map(
                e -> modelMapper.map(e, TaskResponse.class)
        ).collect(Collectors.toList());

        return ResponseMessage.success(data);
    }
}