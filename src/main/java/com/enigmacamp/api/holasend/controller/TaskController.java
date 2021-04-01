package com.enigmacamp.api.holasend.controller;

import com.enigmacamp.api.holasend.configs.exporter.ReportModelMapper;
import com.enigmacamp.api.holasend.configs.exporter.TaskReportExporter;
import com.enigmacamp.api.holasend.configs.jwt.JwtToken;
import com.enigmacamp.api.holasend.entities.CourierActivity;
import com.enigmacamp.api.holasend.entities.Destination;
import com.enigmacamp.api.holasend.entities.Task;
import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.enums.RoleEnum;
import com.enigmacamp.api.holasend.exceptions.*;
import com.enigmacamp.api.holasend.models.CountModel;
import com.enigmacamp.api.holasend.models.FirebaseNotification;
import com.enigmacamp.api.holasend.models.FirebaseResponse;
import com.enigmacamp.api.holasend.models.ResponseMessage;
import com.enigmacamp.api.holasend.models.entitymodels.request.DateRangeRequest;
import com.enigmacamp.api.holasend.models.entitymodels.request.DateRangeWithTokenRequest;
import com.enigmacamp.api.holasend.models.entitymodels.request.TaskRequest;
import com.enigmacamp.api.holasend.models.entitymodels.response.StatusCountResponse;
import com.enigmacamp.api.holasend.models.entitymodels.response.TaskResponse;
import com.enigmacamp.api.holasend.models.entitysearch.MyRequestTaskSearch;
import com.enigmacamp.api.holasend.models.entitysearch.TaskSearch;
import com.enigmacamp.api.holasend.models.excel.TaskReportModel;
import com.enigmacamp.api.holasend.models.pagination.PageSearch;
import com.enigmacamp.api.holasend.models.pagination.PagedList;
import com.enigmacamp.api.holasend.models.validations.DateTimeValidator;
import com.enigmacamp.api.holasend.services.CourierActivityService;
import com.enigmacamp.api.holasend.services.DestinationService;
import com.enigmacamp.api.holasend.services.TaskService;
import com.enigmacamp.api.holasend.services.UserService;
import com.enigmacamp.api.holasend.utils.TokenToUserConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.enigmacamp.api.holasend.controller.validations.RoleValidation.*;
import static com.enigmacamp.api.holasend.enums.RoleEnum.ADMIN;
import static com.enigmacamp.api.holasend.enums.RoleEnum.STAFF;
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


    private String baseUrl = "https://fcm.googleapis.com";

    private WebClient webClient = WebClient.create(baseUrl);

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

        FirebaseNotification notification = new FirebaseNotification();

        String title = "You Have A new Task";
        String body = "Request from: " + user.getUsername() +
                "\nTo: " + entity.getDestination().getName() +
                "\nPriority: " + entity.getPriority() +
                "\nNotes: " + entity.getNotes();

        notification.getNotification().setTitle(title);
        notification.getNotification().setBody(body);

        notification.getData().setTitle(title);
        notification.getData().setMessage(body);


        webClient.post()
                .uri("/fcm/send")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "key=AAAA3fJSimo:APA91bGTgz7ZyCkMfgszZViFE4kzKIaxlITucjlYQZj30He0Co_fTImo55SR77PcjFHMpCygnJG2jI5_WEGCo3l_Bri4cf4QJ73Wa0ANQ9osqNNes7569d1Vg4NLyD9ZC1N3CZIHRObK")
                .bodyValue(notification)
                .retrieve()
                .bodyToMono(FirebaseResponse.class)
                .block();

        return ResponseMessage.success(data);
    }

    @GetMapping("/all")
    public ResponseMessage<List<TaskResponse>> findAllByRange(
            DateRangeRequest model,
            HttpServletRequest request
    ) {
        validateAdmin(request);

        Boolean validDateStart = DateTimeValidator.validate(model.getAfter());
        Boolean validDateEnd = DateTimeValidator.validate(model.getBefore());

        if (!validDateStart || !validDateEnd)
            throw new DateInvalidException();
        model.setBefore(model.getBefore() + " 23:59:59");

        List<Task> taskList = service.findByRange(
                model.getAfter(),
                model.getBefore()
        );

        List<TaskResponse> data = taskList.stream().map(
                e -> modelMapper.map(e, TaskResponse.class)
        ).collect(Collectors.toList());

        return ResponseMessage.success(data);
    }

    @GetMapping("/export")
    public void exportToExcel(
            @Valid DateRangeWithTokenRequest model,
            HttpServletResponse response
    ) throws IOException {

        String username = jwtTokenUtil.getUsernameFromToken(model.getToken());
        User user = userService.findByUsername(username);

        if (!user.getRole().equals(ADMIN))
            throw new InvalidCredentialsException();

        Boolean validDateStart = DateTimeValidator.validate(model.getAfter());
        Boolean validDateEnd = DateTimeValidator.validate(model.getBefore());

        if (!validDateStart || !validDateEnd)
            throw new DateInvalidException();
        model.setAfter(model.getAfter() + " 01:01:01");
        model.setBefore(model.getBefore() + " 23:59:59");

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        LocalDateTime timestamp = LocalDateTime.now();
        String headerValue = "attachment; filename=Report " + timestamp.toString().substring(0, 16) + ".xlsx";

        response.setHeader(headerKey, headerValue);
        List<Task> taskList = service.findByRange(
                model.getAfter(),
                model.getBefore()
        );

        List<TaskReportModel> modelList = ReportModelMapper.convert(taskList);

        TaskReportExporter exp = new TaskReportExporter(modelList);
        exp.export(response);
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

    @GetMapping
    public ResponseMessage<PagedList<TaskResponse>> findAll(
            TaskSearch model,
            HttpServletRequest request
    ) {
        validateAdmin(request);

        Boolean validDateStart = DateTimeValidator.validate(model.getAfter());
        Boolean validDateEnd = DateTimeValidator.validate(model.getBefore());

        if (!validDateStart || !validDateEnd)
            throw new DateInvalidException();
        model.setBefore(model.getBefore() + " 23:59:59");

        model.setPage(model.getSize() * model.getPage());

        List<Task> entities = service.findTasksByCreateDateOrStatusOrDestinationOrRequestByOrPriority(
                model
        );

        Long count = service.countPagination(model);

        List<TaskResponse> responses = entities.stream()
                .map(e -> modelMapper.map(e, TaskResponse.class))
                .collect(Collectors.toList());

        PagedList<TaskResponse> data = new PagedList<>(
                responses,
                Integer.valueOf(model.getPage().toString()),
                Integer.parseInt(model.getSize().toString()),
                Long.parseLong(count.toString())
        );

        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<TaskResponse> removeById(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        validateAdminOrStaff(request);
        User user = findUser(request);
        Task findTask = service.findById(id);

        if (user.getRole().equals(STAFF) && !findTask.getRequestBy().equals(user))
            throw new InvalidCredentialsException();

        if ((findTask.getStatus().equals(PICKUP) || findTask.getStatus().equals(DELIVERED)))
            throw new TaskProceseedException();

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
    public ResponseMessage<PagedList<TaskResponse>> findAllFinishedTask(
            HttpServletRequest request,
            PageSearch search
    ) {
        validateCourier(request);
        User courier = findUser(request);
        List<Task> entities = service.findAllCourierTaskHistory(courier.getId(), search);
        List<TaskResponse> responses = entities.stream()
                .map(e -> modelMapper.map(e, TaskResponse.class))
                .collect(Collectors.toList());

        Long count = service.countAllCourierTaskHistory(courier);

        PagedList<TaskResponse> data = new PagedList<>(
                responses,
                Integer.valueOf(search.getPage().toString()),
                Integer.parseInt(search.getSize().toString()),
                Long.parseLong(count.toString())
        );

        return ResponseMessage.success(data);
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
    public ResponseMessage<PagedList<TaskResponse>> findAllFinishedRequestedTask(
            HttpServletRequest request,
            MyRequestTaskSearch search
    ) {
        validateAdminOrStaff(request);

        Boolean validDateStart = DateTimeValidator.validate(search.getAfter());
        Boolean validDateEnd = DateTimeValidator.validate(search.getBefore());

        if (!validDateStart || !validDateEnd)
            throw new DateInvalidException();
        search.setBefore(search.getBefore() + " 23:59:59");

        User user = findUser(request);
        List<Task> entities = service.findAllFinishedRequestTask(user.getId(), search);
        List<TaskResponse> responses = entities.stream()
                .map(e -> modelMapper.map(e, TaskResponse.class))
                .collect(Collectors.toList());

        Long count = service.countAllFinishedRequestTask(user, search);

        PagedList<TaskResponse> data = new PagedList<>(
                responses,
                Integer.valueOf(search.getPage().toString()),
                Integer.parseInt(search.getSize().toString()),
                Long.parseLong(count.toString())
        );

        return ResponseMessage.success(data);
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

    @GetMapping("/dashboard")
    public ResponseMessage<StatusCountResponse> roleCount(
            HttpServletRequest request
    ) {
        validateAdminOrStaff(request);

        List<Task> lastRequest = service.findByLastCreatedTask();
        List<TaskResponse> lastRequestResponses = lastRequest.stream()
                .map(e -> modelMapper.map(e, TaskResponse.class))
                .collect(Collectors.toList());

        List<Task> lastPickup = service.findByLastPickedUpTask();
        List<TaskResponse> lastPickupResponses = lastPickup.stream()
                .map(e -> modelMapper.map(e, TaskResponse.class))
                .collect(Collectors.toList());

        List<Task> lastDelivered = service.findByLastDeliveredTask();
        List<TaskResponse> lastDeliveredResponses = lastDelivered.stream()
                .map(e -> modelMapper.map(e, TaskResponse.class))
                .collect(Collectors.toList());


        StatusCountResponse response = new StatusCountResponse(
                service.countByStatus(WAITING),
                service.countByStatus(ASSIGNED),
                service.countByStatus(PICKUP),
                service.countByStatus(DELIVERED),
                lastRequestResponses,
                lastPickupResponses,
                lastDeliveredResponses

        );
        return ResponseMessage.success(response);
    }
}