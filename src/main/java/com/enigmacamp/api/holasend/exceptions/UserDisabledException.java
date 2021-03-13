package com.enigmacamp.api.holasend.exceptions;

import org.springframework.http.HttpStatus;

public class UserDisabledException extends ApplicationException {

    public UserDisabledException() {
        super(HttpStatus.valueOf(400), "error." + HttpStatus.valueOf(400).value() + ".user-disabled");
    }
}
