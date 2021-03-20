package com.enigmacamp.api.holasend.controller;

import com.enigmacamp.api.holasend.configs.jwt.JwtToken;
import com.enigmacamp.api.holasend.entities.Destination;
import com.enigmacamp.api.holasend.exceptions.DestinationAlreadyExistsException;
import com.enigmacamp.api.holasend.exceptions.EntityNotFoundException;
import com.enigmacamp.api.holasend.models.ResponseMessage;
import com.enigmacamp.api.holasend.models.entitymodels.request.DestinationRequest;
import com.enigmacamp.api.holasend.models.entitymodels.response.DestinationResponse;
import com.enigmacamp.api.holasend.models.entitysearch.DestinationSearch;
import com.enigmacamp.api.holasend.models.pagination.PagedList;
import com.enigmacamp.api.holasend.services.DestinationService;
import com.enigmacamp.api.holasend.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.enigmacamp.api.holasend.controller.validations.RoleValidation.validateRoleAdmin;
import static com.enigmacamp.api.holasend.controller.validations.RoleValidation.validateRoleAdminOrStaff;

@RequestMapping("/destinations")
@RestController
public class DestinationController {

    @Autowired
    private DestinationService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtToken jwtTokenUtil;

    private void validateAdmin(HttpServletRequest request) {
        validateRoleAdmin(request, jwtTokenUtil, userService);
    }

    private void validateAdminOrStaff(HttpServletRequest request) {
        validateRoleAdminOrStaff(request, jwtTokenUtil, userService);
    }

    @GetMapping("/{id}")
    public ResponseMessage<DestinationResponse> findById(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        validateAdminOrStaff(request);
        Destination entity = service.findById(id);
        if(entity != null) {
            DestinationResponse data = modelMapper.map(entity, DestinationResponse.class);
            return ResponseMessage.success(data);
        }
        throw new EntityNotFoundException();
    }

    @PostMapping
    public ResponseMessage<DestinationResponse> add(
            @RequestBody @Valid DestinationRequest model,
            HttpServletRequest request
    ) {
        validateAdminOrStaff(request);
        Destination oldDestination = service.findByName(model.getName());

        System.out.println(oldDestination);

        if (oldDestination != null) {
            throw new DestinationAlreadyExistsException();
        }

        Destination entity = modelMapper.map(model, Destination.class);
        entity = service.save(entity);

        DestinationResponse data = modelMapper.map(entity, DestinationResponse.class);
        System.out.println("SUCCESS");
        return ResponseMessage.success(data);
    }

    @PutMapping("{id}")
    public ResponseMessage<DestinationResponse> edit(
            @PathVariable String id,
            @RequestBody @Valid DestinationRequest model,
            HttpServletRequest request
    ) {
        validateAdmin(request);
        Destination entity = service.findById(id);
        if(entity == null) {
            throw new EntityNotFoundException();
        }
        modelMapper.map(model, entity);
        entity = service.save(entity);

        DestinationResponse data = modelMapper.map(entity, DestinationResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<DestinationResponse> delete(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        validateAdmin(request);
        Destination entity = service.removeById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }

        DestinationResponse data = modelMapper.map(entity, DestinationResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/all")
    public ResponseMessage<List<DestinationResponse>> findAll(
            HttpServletRequest request
    ) {
        validateAdminOrStaff(request);
        List<Destination> entities = service.findAll();
        List<DestinationResponse> responses = entities.stream()
                .map(e -> modelMapper.map(e, DestinationResponse.class))
                .collect(Collectors.toList());

        return ResponseMessage.success(responses);
    }

    @GetMapping
    public ResponseMessage<PagedList<DestinationResponse>> findAll(
            DestinationSearch model,
            HttpServletRequest request
            ) {
        validateAdminOrStaff(request);
        Destination search = modelMapper.map(model, Destination.class);

        Page<Destination> entityPage = service.findAll(
                search, Integer.parseInt(model.getPage().toString()), Integer.parseInt(model.getSize().toString()), model.getSort()
        );
        List<Destination> entities = entityPage.toList();

        List<DestinationResponse> response = entities.stream()
                .map(e -> modelMapper.map(e, DestinationResponse.class))
                .collect(Collectors.toList());

        PagedList<DestinationResponse> data = new PagedList<>(
                response,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements()
        );

        return ResponseMessage.success(data);
    }
}
