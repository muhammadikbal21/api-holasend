package com.enigmacamp.api.holasend.utils;

import com.enigmacamp.api.holasend.configs.jwt.JwtToken;
import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.exceptions.InvalidPermissionsException;
import com.enigmacamp.api.holasend.services.UserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class TokenToUserConverter {

    public User convertToken(HttpServletRequest request, UserService service, JwtToken jwtTokenUtil) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new InvalidPermissionsException();
        }
        token = token.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        return service.findByUsername(username);
    }
}
