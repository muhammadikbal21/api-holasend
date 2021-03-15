package com.enigmacamp.api.holasend.controller;

import com.enigmacamp.api.holasend.configs.jwt.JwtToken;
import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.entities.UserDetails;
import com.enigmacamp.api.holasend.enums.RoleEnum;
import com.enigmacamp.api.holasend.exceptions.EntityNotFoundException;
import com.enigmacamp.api.holasend.exceptions.InvalidCredentialsException;
import com.enigmacamp.api.holasend.exceptions.InvalidPermissionsException;
import com.enigmacamp.api.holasend.exceptions.UsernameExistException;
import com.enigmacamp.api.holasend.models.ResponseMessage;
import com.enigmacamp.api.holasend.models.entitymodels.elements.UserElement;
import com.enigmacamp.api.holasend.models.entitymodels.request.UserChangePasswordRequest;
import com.enigmacamp.api.holasend.models.entitymodels.request.UserWithUserDetailsRequest;
import com.enigmacamp.api.holasend.models.entitymodels.response.UserResponse;
import com.enigmacamp.api.holasend.models.entitysearch.UserSearch;
import com.enigmacamp.api.holasend.models.pagination.PagedList;
import com.enigmacamp.api.holasend.services.UserDetailsService;
import com.enigmacamp.api.holasend.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.enigmacamp.api.holasend.controller.validations.RoleValidation.validateNotDisabled;
import static com.enigmacamp.api.holasend.controller.validations.RoleValidation.validateRoleAdmin;
import static com.enigmacamp.api.holasend.enums.RoleEnum.*;


@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtToken jwtTokenUtil;

    private void validateAdmin(User loggedInUser, User user) {
        if (loggedInUser.getUsername().equals(user.getUsername()) || !loggedInUser.getRole().equals(ADMIN))
            throw new InvalidPermissionsException();
    }

    private ResponseMessage<UserResponse> changeRole(String username, String token, RoleEnum role) {
        if (token != null && token.startsWith("Bearer ")) {
            User user = service.findByUsername(username);
            token = token.substring(7);
            String loggedInUsername = jwtTokenUtil.getUsernameFromToken(token);
            User loggedInUser = service.findByUsername(loggedInUsername);

            validateAdmin(loggedInUser, user);

            user.setRole(role);
            service.save(user);

            UserResponse data = modelMapper.map(user, UserResponse.class);
            return ResponseMessage.success(data);
        }
        throw new InvalidPermissionsException();
    }

    @PostMapping("/register")
    public ResponseMessage<UserResponse> addWithUser(
            @RequestBody @Valid UserWithUserDetailsRequest model
    ) {
        if (service.existsByUsername(model.getUser().getUsername())) {
            throw new UsernameExistException();
        }

        UserDetails userDetails = modelMapper.map(model.getUserDetails(), UserDetails.class);
        userDetails = userDetailsService.save(userDetails);

        User user = modelMapper.map(model.getUser(), User.class);
        user.setUserDetails(userDetails);
        String hashPassword = new BCryptPasswordEncoder().encode(model.getUser().getPassword());
        user.setPassword(hashPassword);
        user.setRole(UNASSIGNED);

        service.save(user);

        UserResponse data = modelMapper.map(user, UserResponse.class);
        return ResponseMessage.success(data);
    }


    @PutMapping("/{username}/disable-user")
    public ResponseMessage<UserResponse> makeUnassigned(
            @PathVariable String username,
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");
        return changeRole(username, token, DISABLED);
    }

    @PutMapping("/{username}/make-courier")
    public ResponseMessage<UserResponse> makeCourier(
            @PathVariable String username,
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");
        return changeRole(username, token, COURIER);
    }

    @PutMapping("/{username}/make-staff")
    public ResponseMessage<UserResponse> makeStaff(
            @PathVariable String username,
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");
        return changeRole(username, token, STAFF);
    }

    @PutMapping("/{username}/make-admin")
    public ResponseMessage<UserResponse> makeAdmin(
            @PathVariable String username,
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");
        return changeRole(username, token, ADMIN);
    }

    @PutMapping("/reset-password/{token}")
    public ResponseMessage<UserResponse> edit(
            @PathVariable String token,
            @RequestBody UserChangePasswordRequest model
    ) {
        String username = jwtTokenUtil.getUsernameFromToken(token);

        User user = service.findByUsername(username);

        if (!token.equals(user.getToken()))
            throw new InvalidCredentialsException();

        String password = model.getPassword();
        String encodedPassword = new BCryptPasswordEncoder().encode(password);

        user.setToken(null);
        user.setPassword(encodedPassword);
        service.save(user);
        UserResponse data = modelMapper.map(user, UserResponse.class);

        return ResponseMessage.success(data);
    }

    @GetMapping("/me")
    public ResponseMessage<UserResponse> me(
            HttpServletRequest request
    ) {
        validateNotDisabled(request, jwtTokenUtil, service);
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            String username = jwtTokenUtil.getUsernameFromToken(token);

            User user = service.findByUsername(username);

            if(user != null) {
                UserResponse data = modelMapper.map(user, UserResponse.class);
                return ResponseMessage.success(data);
            }
        }
        throw new EntityNotFoundException();
    }


    @GetMapping("/{id}")
    public ResponseMessage<UserResponse> findById(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        validateNotDisabled(request, jwtTokenUtil, service);
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            String username = jwtTokenUtil.getUsernameFromToken(token);

            User loggedInUser = service.findByUsername(username);

            User user = service.findById(id);
            UserResponse data = modelMapper.map(user, UserResponse.class);
            if (loggedInUser.getId().equals(id) || loggedInUser.getRole().equals(ADMIN)) {
                return ResponseMessage.success(data);
            }
        }
        throw new EntityNotFoundException();
    }


    @GetMapping("/all")
    public ResponseMessage<List<UserResponse>> findAll(
            HttpServletRequest request
    ) {
        validateRoleAdmin(request, jwtTokenUtil, service);
        List<User> entities = service.findAll();
        List<UserResponse> responses = entities.stream()
                .map(e -> modelMapper.map(e, UserResponse.class))
                .collect(Collectors.toList());

        return ResponseMessage.success(responses);
    }

    @GetMapping
    public ResponseMessage<PagedList<UserElement>> findAll(
            UserSearch model,
            HttpServletRequest request
    ) {
        validateRoleAdmin(request, jwtTokenUtil, service);
        User search = modelMapper.map(model, User.class);

        Page<User> entityPage = service.findAll(
                search, model.getPage(), model.getSize(), model.getSort()
        );
        List<User> entities = entityPage.toList();

        List<UserElement> models = entities.stream()
                .map(e -> modelMapper.map(e, UserElement.class))
                .collect(Collectors.toList());

        PagedList<UserElement> data = new PagedList<>(
                models,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements()
        );

        return ResponseMessage.success(data);
    }
}
