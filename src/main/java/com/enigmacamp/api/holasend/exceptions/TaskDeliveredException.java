package com.enigmacamp.api.holasend.exceptions;

import org.springframework.http.HttpStatus;

public class TaskDeliveredException extends ApplicationException {

    public TaskDeliveredException() {
        super(HttpStatus.valueOf(400), "error." + HttpStatus.valueOf(400).value() + ".task-delivered");
    }
}