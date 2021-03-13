package com.enigmacamp.api.holasend.controller.auth;

import com.enigmacamp.api.holasend.configs.jwt.JwtToken;
import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.exceptions.InvalidCredentialsException;
import com.enigmacamp.api.holasend.models.ResponseMessage;
import com.enigmacamp.api.holasend.models.TokenWithRoleModel;
import com.enigmacamp.api.holasend.models.entitymodels.request.UserLoginRequest;
import com.enigmacamp.api.holasend.repositories.UserRepository;
import com.enigmacamp.api.holasend.services.jwt.UserJwtService;
import com.enigmacamp.api.holasend.services.mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

import static com.enigmacamp.api.holasend.enums.RoleEnum.DISABLED;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserJwtService service;

    @Autowired
    private UserRepository repository;

    @Autowired
    private EmailService emailService;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseMessage<TokenWithRoleModel> createAuthenticationToken(@RequestBody UserLoginRequest authenticationRequest) {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        User user = repository.findByUsername(authenticationRequest.getUsername());

        if (user.getToken() != null) {
            user.setToken(null);
            repository.save(user);
        }

        if (user.getRole().equals(DISABLED))
            throw new InvalidCredentialsException();

        final UserDetails userDetails = service
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtToken.generateToken(userDetails, 30 * 24);

        TokenWithRoleModel data = new TokenWithRoleModel(token, user.getRole());

        return ResponseMessage.success(data);
    }

    @GetMapping("/forget-password/{username}")
    public ResponseMessage<String> forgetPassword(
            @PathVariable String username
    ) throws MessagingException {
        User user = repository.findByUsername(username);
        String secretActivationCode = new BCryptPasswordEncoder().encode(user.getPassword());

        user.setToken(secretActivationCode);
        String email = user.getEmail();

        emailService.sendTokenToEmail(email, username, secretActivationCode);
        repository.save(user);
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