package com.enigmacamp.api.holasend.controller;

import com.enigmacamp.api.holasend.configs.jwt.JwtToken;
import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.entities.UserDetails;
import com.enigmacamp.api.holasend.exceptions.InvalidPermissionsException;
import com.enigmacamp.api.holasend.models.ResponseMessage;
import com.enigmacamp.api.holasend.models.entitymodels.request.UserDetailsRequest;
import com.enigmacamp.api.holasend.models.entitymodels.response.UserDetailsResponse;
import com.enigmacamp.api.holasend.models.entitysearch.UserDetailsSearch;
import com.enigmacamp.api.holasend.models.pagination.PagedList;
import com.enigmacamp.api.holasend.services.UserDetailsService;
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
import static com.enigmacamp.api.holasend.enums.RoleEnum.ADMIN;

@RequestMapping("/user-details")
@RestController
public class UserDetailsController {

    @Autowired
    private UserDetailsService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtToken jwtTokenUtil;

    @GetMapping("/{id}")
    public ResponseMessage<UserDetailsResponse> findById(
            @PathVariable String id, HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            User loggedInUser = userService.findByUsername(username);
            UserDetails userDetails = service.findById(id);

            UserDetailsResponse data = modelMapper.map(userDetails, UserDetailsResponse.class);

            if (loggedInUser.getUserDetails().getId().equals(id) || loggedInUser.getRole().equals(ADMIN)) {
                return ResponseMessage.success(data);
            }
        }
        throw new InvalidPermissionsException();
    }

    @PutMapping("{id}")
    public ResponseMessage<UserDetailsResponse> edit(
            @PathVariable String id,
            @RequestBody @Valid UserDetailsRequest model,
            HttpServletRequest request
    ) {
        UserDetails entity = service.findById(id);

        String token = request.getHeader("Authorization");
        if (entity != null && token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            User user = userService.findByUsername(username);

            if (entity.getId().equals(user.getUserDetails().getId())) {
                modelMapper.map(model, entity);
                entity = service.save(entity);

                UserDetailsResponse data = modelMapper.map(entity, UserDetailsResponse.class);
                return ResponseMessage.success(data);
            }
        }
        throw new InvalidPermissionsException();
    }

    @GetMapping("/all")
    public ResponseMessage<List<UserDetailsResponse>> findAll(
            HttpServletRequest request
    ) {
        validateRoleAdmin(request, jwtTokenUtil, userService);
        List<UserDetails> entities = service.findAll();
        List<UserDetailsResponse> responses = entities.stream()
                .map(e -> modelMapper.map(e, UserDetailsResponse.class))
                .collect(Collectors.toList());

        return ResponseMessage.success(responses);
    }

    @GetMapping
    public ResponseMessage<PagedList<UserDetailsResponse>> findAll(
            UserDetailsSearch model,
            HttpServletRequest request
    ) {
        validateRoleAdmin(request, jwtTokenUtil, userService);
        UserDetails search = modelMapper.map(model, UserDetails.class);

        Page<UserDetails> entityPage = service.findAll(
                search, Integer.parseInt(model.getPage().toString()), Integer.parseInt(model.getSize().toString()), model.getSort()
        );
        List<UserDetails> entities = entityPage.toList();

        List<UserDetailsResponse> models = entities.stream()
                .map(e -> modelMapper.map(e, UserDetailsResponse.class))
                .collect(Collectors.toList());

        PagedList<UserDetailsResponse> data = new PagedList<>(
                models,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements()
        );

        return ResponseMessage.success(data);
    }
}