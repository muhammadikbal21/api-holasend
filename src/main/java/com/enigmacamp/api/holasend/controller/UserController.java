package com.enigmacamp.api.holasend.controller;

import com.enigmacamp.api.holasend.configs.jwt.JwtToken;
import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.entities.UserDetails;
import com.enigmacamp.api.holasend.exceptions.EntityNotFoundException;
import com.enigmacamp.api.holasend.exceptions.InvalidCredentialsException;
import com.enigmacamp.api.holasend.exceptions.InvalidPermissionsException;
import com.enigmacamp.api.holasend.models.ResponseMessage;
import com.enigmacamp.api.holasend.models.entitymodels.request.UserChangePasswordRequest;
import com.enigmacamp.api.holasend.models.entitymodels.request.UserWithUserDetailsRequest;
import com.enigmacamp.api.holasend.models.entitymodels.response.UserResponse;
import com.enigmacamp.api.holasend.repositories.UserRepository;
import com.enigmacamp.api.holasend.services.UserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;

import static com.enigmacamp.api.holasend.enums.RoleEnum.*;


@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserDetailsService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtToken jwtTokenUtil;

    @PostMapping("/register")
    public ResponseMessage<UserResponse> addWithUser(
            @RequestBody @Valid UserWithUserDetailsRequest model
    ) {
        if (repository.existsByUsername(model.getUser().getUsername())) {
            throw new ValidationException("Username already existed");
        }

        UserDetails userDetails = modelMapper.map(model.getUserDetails(), UserDetails.class);
        userDetails = service.save(userDetails);

        User user = new User();
        user.setUsername(model.getUser().getUsername());
        user.setUserDetails(userDetails);
        user.setEmail(model.getUser().getEmail());
        String hashPassword = new BCryptPasswordEncoder().encode(model.getUser().getPassword());
        user.setPassword(hashPassword);
        user.setRole(UNASSIGNED);

        repository.save(user);

        UserResponse data = modelMapper.map(user, UserResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/{username}/make-customer")
    public ResponseMessage<UserResponse> makeCustomer(
            @PathVariable String username,
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            User user = repository.findByUsername(username);
            token = token.substring(7);
            String loggedInUsername = jwtTokenUtil.getUsernameFromToken(token);
            User loggedInUser = repository.findByUsername(loggedInUsername);

            if (loggedInUser.getUsername().equals(user.getUsername()) || loggedInUser.getRole().equals(UNASSIGNED))
                throw new InvalidPermissionsException();

            user.setRole(UNASSIGNED);
            repository.save(user);

            UserResponse data = modelMapper.map(user, UserResponse.class);
            return ResponseMessage.success(data);
        }
        throw new InvalidPermissionsException();
    }

    @GetMapping("/{username}/make-courier")
    public ResponseMessage<UserResponse> makeCourier(
            @PathVariable String username,
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            User user = repository.findByUsername(username);
            token = token.substring(7);
            String loggedInUsername = jwtTokenUtil.getUsernameFromToken(token);
            User loggedInUser = repository.findByUsername(loggedInUsername);

            if (loggedInUser.getUsername().equals(user.getUsername()) || loggedInUser.getRole().equals(UNASSIGNED) || loggedInUser.getRole().equals(COURIER))
                throw new InvalidPermissionsException();

            user.setRole(COURIER);
            repository.save(user);

            UserResponse data = modelMapper.map(user, UserResponse.class);
            return ResponseMessage.success(data);
        }
        throw new InvalidPermissionsException();
    }

    @GetMapping("/{username}/make-admin")
    public ResponseMessage<UserResponse> makeAdmin(
            @PathVariable String username,
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            User user = repository.findByUsername(username);
            token = token.substring(7);
            String loggedInUsername = jwtTokenUtil.getUsernameFromToken(token);
            User loggedInUser = repository.findByUsername(loggedInUsername);

            if (!loggedInUser.getRole().equals(ADMIN)) {
                throw new InvalidPermissionsException();
            }
            user.setRole(ADMIN);
            repository.save(user);

            UserResponse data = modelMapper.map(user, UserResponse.class);
            return ResponseMessage.success(data);
        }
        throw new InvalidPermissionsException();
    }

    @PutMapping("/reset-password/{token}")
    public ResponseMessage<UserResponse> edit(
            @PathVariable String token,
            @RequestBody UserChangePasswordRequest model
    ) {
        String username = jwtTokenUtil.getUsernameFromToken(token);

        User user = repository.findByUsername(username);

        if (!token.equals(user.getToken())) {
            throw new InvalidCredentialsException();
        }

        String password = model.getPassword();
        String encodedPassword = new BCryptPasswordEncoder().encode(password);

        user.setToken(null);
        user.setPassword(encodedPassword);
        repository.save(user);
        UserResponse data = modelMapper.map(user, UserResponse.class);

        return ResponseMessage.success(data);
    }

    @GetMapping("/me")
    public ResponseMessage<UserResponse> findById(
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            String username = jwtTokenUtil.getUsernameFromToken(token);

            User user = repository.findByUsername(username);

            if(user != null) {
                UserResponse data = modelMapper.map(user, UserResponse.class);
                return ResponseMessage.success(data);
            }
        }
        throw new EntityNotFoundException();
    }
}
