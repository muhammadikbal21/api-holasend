package com.enigmacamp.api.holasend.controller.validations;

import com.enigmacamp.api.holasend.configs.jwt.JwtToken;
import com.enigmacamp.api.holasend.entities.User;
import com.enigmacamp.api.holasend.enums.RoleEnum;
import com.enigmacamp.api.holasend.exceptions.InvalidPermissionsException;
import com.enigmacamp.api.holasend.exceptions.UserDisabledException;
import com.enigmacamp.api.holasend.services.UserService;

import javax.servlet.http.HttpServletRequest;

import static com.enigmacamp.api.holasend.enums.RoleEnum.*;

public class RoleValidation {
    public static void validateRoleAdmin(HttpServletRequest request, JwtToken jwtTokenUtil, UserService userService) {
        validateRole(request, jwtTokenUtil, userService, ADMIN);
    }

    public static void validateRoleStaff(HttpServletRequest request, JwtToken jwtTokenUtil, UserService userService) {
        validateRole(request, jwtTokenUtil, userService, STAFF);
    }

    public static void validateRoleAdminOrStaff(HttpServletRequest request, JwtToken jwtTokenUtil, UserService userService) {
        String username = startWithBearer(request, jwtTokenUtil);
        User user = userService.findByUsername(username);
        if (!(user.getRole().equals(STAFF) || user.getRole().equals(ADMIN))) {
            throw new InvalidPermissionsException();
        }
    }

    public static void validateRoleMinimumCourier(HttpServletRequest request, JwtToken jwtTokenUtil, UserService userService) {
        String username = startWithBearer(request, jwtTokenUtil);
        User user = userService.findByUsername(username);
        if (!(user.getRole().equals(STAFF) || user.getRole().equals(ADMIN) || user.getRole().equals(COURIER))) {
            throw new InvalidPermissionsException();
        }
    }

    public static void validateRoleCourier(HttpServletRequest request, JwtToken jwtTokenUtil, UserService userService) {
        validateRole(request, jwtTokenUtil, userService, COURIER);
    }

    private static void validateRole(HttpServletRequest request, JwtToken jwtTokenUtil, UserService userService, RoleEnum role) {
        String username = startWithBearer(request, jwtTokenUtil);
        User user = userService.findByUsername(username);
        if (!user.getRole().equals(role)) {
            throw new InvalidPermissionsException();
        }
    }

    public static void validateNotDisabled(HttpServletRequest request, JwtToken jwtTokenUtil, UserService userService) {
        String username = startWithBearer(request, jwtTokenUtil);
        User user = userService.findByUsername(username);
        if (user.getRole().equals(DISABLED)) {
            throw new UserDisabledException();
        }
    }

    public static String startWithBearer(HttpServletRequest request, JwtToken jwtTokenUtil) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new InvalidPermissionsException();
        }
        token = token.substring(7);
        return jwtTokenUtil.getUsernameFromToken(token);
    }
}
