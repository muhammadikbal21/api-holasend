package com.enigmacamp.api.holasend.exceptions;

import org.springframework.http.HttpStatus;

public class ActiveActivityException extends ApplicationException {

    public ActiveActivityException() {
        super(HttpStatus.NOT_FOUND, "error." + HttpStatus.NOT_FOUND.value() + ".active-activity");
    }
}