package com.enigmacamp.api.holasend.controller.auth;

import com.enigmacamp.api.holasend.configs.jwt.JwtToken;
import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.exceptions.InvalidCredentialsException;
import com.enigmacamp.api.holasend.models.ResponseMessage;
import com.enigmacamp.api.holasend.models.entitymodels.request.UserLoginRequest;
import com.enigmacamp.api.holasend.models.jwt.JwtResponse;
import com.enigmacamp.api.holasend.repositories.UserRepository;
import com.enigmacamp.api.holasend.services.UserService;
import com.enigmacamp.api.holasend.services.mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    @Autowired
    private EmailService emailService;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseMessage<JwtResponse> createAuthenticationToken(@RequestBody UserLoginRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = service
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtToken.generateToken(userDetails, 30 * 24);

        return ResponseMessage.success(new JwtResponse(token));
    }

    @GetMapping("/forget-password/{username}")
    public ResponseMessage<String> forgetPassword(
            @PathVariable String username
    ) throws MessagingException {
        UserDetails userDetails = service.loadUserByUsername(username);
        String token = jwtToken.generateToken(userDetails, 1);

        User user = repository.findByUsername(username);
        user.setToken(token);
        String email = user.getEmail();

        emailService.sendTokenToEmail(email, username, token);
        repository.save(user);

        System.out.println(token);
        return ResponseMessage.success(email);
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException | BadCredentialsException e) {
            throw new InvalidCredentialsException();
        }
    }
}