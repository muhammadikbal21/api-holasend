package com.enigmacamp.api.holasend.exceptions;

import org.springframework.http.HttpStatus;

public class ZeroUnfinishedTask extends ApplicationException {

    public ZeroUnfinishedTask() {
        super(HttpStatus.valueOf(400), "error." + HttpStatus.valueOf(400).value() + ".zero-unfinished-task");
    }
}