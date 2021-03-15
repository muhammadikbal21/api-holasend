package com.enigmacamp.api.holasend.controller.errorhandler;

import com.enigmacamp.api.holasend.exceptions.TokenExpiredException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultErrorController implements ErrorController {

    @GetMapping("/error")
    public void handleError() {
        throw new TokenExpiredException();
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
