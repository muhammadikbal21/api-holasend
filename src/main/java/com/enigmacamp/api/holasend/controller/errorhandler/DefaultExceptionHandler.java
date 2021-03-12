package com.enigmacamp.api.holasend.controller.errorhandler;

import com.enigmacamp.api.holasend.exceptions.ApplicationException;
import com.enigmacamp.api.holasend.models.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    private ResponseEntity<Object> handleBindingResult(BindingResult result, HttpStatus status) {
        Map<String, List<String>> errors = new HashMap<>();
        result.getFieldErrors().forEach(fieldError -> {
            String name = fieldError.getField();
            String value = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

            List<String> messages = errors.computeIfAbsent(name, k -> new ArrayList<>());
            messages.add(value);
        });

        String message = messageSource.getMessage("error." + status.value(),
                null, LocaleContextHolder.getLocale());
        ResponseMessage<?> body = ResponseMessage.error(status.value(), message, errors);
        return ResponseEntity.ok(body);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleBindingResult(ex, status);
    }

    private ResponseEntity<Object> handleException(HttpStatus status){
        String message = "error." + status.value();
        return handleException(status, message);
    }

    private ResponseEntity<Object> handleException(HttpStatus status, String message) {
        String localizedMessage = messageSource
                .getMessage(message, null, message, LocaleContextHolder.getLocale());

        ResponseMessage<?> body = ResponseMessage.error(status.value(), localizedMessage);
        return  ResponseEntity.ok(body);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(status);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException e) {
        return handleException(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnknownException(Exception e) {
        logger.error("Unknown exception: ", e);
        return handleException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
