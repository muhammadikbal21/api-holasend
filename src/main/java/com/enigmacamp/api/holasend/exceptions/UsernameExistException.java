package com.enigmacamp.api.holasend.exceptions;

import org.springframework.http.HttpStatus;

public class UsernameExistException extends ApplicationException {

    public UsernameExistException() {
        super(HttpStatus.valueOf(500), "error." + HttpStatus.valueOf(500).value() + ".username-exist");
    }
}
