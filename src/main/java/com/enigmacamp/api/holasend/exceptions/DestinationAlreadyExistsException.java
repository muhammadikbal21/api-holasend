package com.enigmacamp.api.holasend.exceptions;

import org.springframework.http.HttpStatus;

public class DestinationAlreadyExistsException extends ApplicationException {

    public DestinationAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, "error." + HttpStatus.BAD_REQUEST.value() + ".destination-already-exist");
    }
}