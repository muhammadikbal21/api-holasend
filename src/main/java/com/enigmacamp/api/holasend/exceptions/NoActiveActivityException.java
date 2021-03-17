package com.enigmacamp.api.holasend.exceptions;

import org.springframework.http.HttpStatus;

public class NoActiveActivityException extends ApplicationException {

    public NoActiveActivityException() {
        super(HttpStatus.NOT_FOUND, "error." + HttpStatus.NOT_FOUND.value() + ".no-active-activity");
    }
}