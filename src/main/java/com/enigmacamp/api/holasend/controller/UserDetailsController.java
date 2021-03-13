package com.enigmacamp.api.holasend.controller;

import com.enigmacamp.api.holasend.configs.jwt.JwtToken;
import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.entities.UserDetails;
import com.enigmacamp.api.holasend.exceptions.InvalidPermissionsException;
import com.enigmacamp.api.holasend.models.ResponseMessage;
import com.enigmacamp.api.holasend.models.entitymodels.request.UserDetailsRequest;
import com.enigmacamp.api.holasend.models.entitymodels.response.UserDetailsResponse;
import com.enigmacamp.api.holasend.repositories.UserRepository;
import com.enigmacamp.api.holasend.services.UserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping("/user-details")
@RestController
public class UserDetailsController {

    @Autowired
    private UserDetailsService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

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
            User user = userRepository.findByUsername(username);

            UserDetailsResponse data = modelMapper.map(user.getUserDetails(), UserDetailsResponse.class);

            if (user.getUserDetails().getId().equals(id)) {
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
            User user = userRepository.findByUsername(username);

            if (entity.getId().equals(user.getUserDetails().getId())) {
                modelMapper.map(model, entity);
                entity = service.save(entity);

                UserDetailsResponse data = modelMapper.map(entity, UserDetailsResponse.class);
                return ResponseMessage.success(data);
            }
        }
        throw new InvalidPermissionsException();
    }
}