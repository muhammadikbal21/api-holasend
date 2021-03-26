package com.enigmacamp.api.holasend.controller.auth;

import com.enigmacamp.api.holasend.configs.jwt.JwtToken;
import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.exceptions.InvalidCredentialsException;
import com.enigmacamp.api.holasend.exceptions.InvalidPermissionsException;
import com.enigmacamp.api.holasend.exceptions.UserDisabledException;
import com.enigmacamp.api.holasend.models.ResponseMessage;
import com.enigmacamp.api.holasend.models.TokenWithUsernameAndRoleModel;
import com.enigmacamp.api.holasend.models.entitymodels.request.ChangePasswordRequest;
import com.enigmacamp.api.holasend.models.entitymodels.request.UserLoginRequest;
import com.enigmacamp.api.holasend.models.entitymodels.response.UserResponse;
import com.enigmacamp.api.holasend.repositories.UserRepository;
import com.enigmacamp.api.holasend.services.UserService;
import com.enigmacamp.api.holasend.services.jwt.UserJwtService;
import com.enigmacamp.api.holasend.services.mail.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseMessage<TokenWithUsernameAndRoleModel> createAuthenticationToken(@RequestBody UserLoginRequest authenticationRequest) {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        User user = userService.findByUsername(authenticationRequest.getUsername());

        if (user.getToken() != null) {
            user.setToken(null);
            userService.save(user);
        }

        if (user.getRole().equals(DISABLED))
            throw new UserDisabledException();

        final UserDetails userDetails = service
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtToken.generateToken(userDetails, 30 * 24);

        TokenWithUsernameAndRoleModel data = new TokenWithUsernameAndRoleModel(token, user.getUsername(), user.getRole());

        return ResponseMessage.success(data);
    }

    @GetMapping("/forget-password/{username}")
    public ResponseMessage<String> forgetPassword(
            @PathVariable String username
    ) throws MessagingException {
        User user = userService.findByUsername(username);
        final UserDetails userDetails = service
                .loadUserByUsername(username);

        final String secretActivationCode = jwtToken.generateToken(userDetails, 30 * 24);
        user.setToken(secretActivationCode);
        String email = user.getEmail();

        emailService.sendTokenToEmail(email, username, secretActivationCode);
        userService.save(user);
        return ResponseMessage.success(email);
    }

    @PutMapping("/change-password")
    public ResponseMessage<UserResponse> changePassword(
            @Valid @RequestBody ChangePasswordRequest model,
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new InvalidPermissionsException();
        }
        token = token.substring(7);
        String username = jwtToken.getUsernameFromToken(token);
        User user = userService.findByUsername(username);

        if (user.getRole().equals(DISABLED))
            throw new UserDisabledException();

        authenticate(user.getUsername(), model.getOldPassword());

        String hashPassword = new BCryptPasswordEncoder().encode(model.getNewPassword());
        user.setPassword(hashPassword);
        userService.save(user);

        UserResponse response = modelMapper.map(user, UserResponse.class);

        return ResponseMessage.success(response);
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException | BadCredentialsException e) {
            throw new InvalidCredentialsException();
        }
    }
}