package com.enigmacamp.api.holasend.controller;

import com.enigmacamp.api.holasend.configs.jwt.JwtToken;
import com.enigmacamp.api.holasend.models.ResponseMessage;
import com.enigmacamp.api.holasend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.enigmacamp.api.holasend.controller.validations.RoleValidation.validateRoleAdmin;

@RestController
@RequestMapping("/offset")
public class OffsetController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtToken jwtTokenUtil;

    private Double offset = 100.0;

    private void validateAdmin(HttpServletRequest request) {
        validateRoleAdmin(request, jwtTokenUtil, userService);
    }

    @GetMapping
    public ResponseMessage<Double> getOffset() {
        return ResponseMessage.success(offset);
    }

    @PutMapping("/{input}")
    public ResponseMessage<Double> editOffset(
            HttpServletRequest request,
            @PathVariable Double input
    ) {
        validateAdmin(request);
        offset = input;
        return ResponseMessage.success(offset);
    }
}
