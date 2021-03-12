package com.enigmacamp.api.holasend.exceptions;

import org.springframework.http.HttpStatus;

public class DateInvalidException extends ApplicationException {

    public DateInvalidException() {
        super(HttpStatus.valueOf(400), "error." + HttpStatus.valueOf(400).value() + ".date-invalid");
    }
}