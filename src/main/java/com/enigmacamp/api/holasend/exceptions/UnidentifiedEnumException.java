package com.enigmacamp.api.holasend.exceptions;

import org.springframework.http.HttpStatus;

public class UnidentifiedEnumException extends ApplicationException {

    public UnidentifiedEnumException() {
        super(HttpStatus.NOT_FOUND, "error." + HttpStatus.NOT_FOUND.value() + ".unidentified-enum");
    }
}