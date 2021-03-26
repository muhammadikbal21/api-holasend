package com.enigmacamp.api.holasend.utils;

import com.enigmacamp.api.holasend.configs.jwt.JwtToken;
import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.repositories.UserRepository;
import com.enigmacamp.api.holasend.services.UserService;
import com.enigmacamp.api.holasend.services.jwt.UserJwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UtilTest {

    @MockBean
    private TokenToUserConverter converter;

    @MockBean
    private JwtToken jwtToken;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private UserRepository userRepository;

    @MockBean
    private UserJwtService userJwtService;

    @MockBean
    private HttpServletRequest request;

    private User user;

    @BeforeEach
    void init() {
        String anyString = "x";
        String username = anyString;

        user = new User();
        user.setUsername(username);
    }

    @Test
    void converterShouldReturnUsername() {
        userService.save(user);
        UserDetails userDetails = userJwtService.loadUserByUsername(user.getUsername());

        String token = "Bearer ";
        token += jwtToken.generateToken(userDetails, 1);

        when(request.getHeader("Authorization")).thenReturn(token);

        User convertedUser = converter.convertToken(request, userService, jwtToken);

        assertEquals(convertedUser.getUsername(), user.getUsername());
    }

}
