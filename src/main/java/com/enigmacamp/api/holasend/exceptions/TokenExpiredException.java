package com.enigmacamp.api.holasend.exceptions;

import org.springframework.http.HttpStatus;

public class TokenExpiredException extends ApplicationException {

    public TokenExpiredException() {
        super(HttpStatus.valueOf(401), "error." + HttpStatus.valueOf(401).value() + ".token-expired");
    }
}
