package com.enigmacamp.api.holasend.exceptions;

import org.springframework.http.HttpStatus;

public class TaskDidntStartedException extends ApplicationException {

    public TaskDidntStartedException() {
        super(HttpStatus.valueOf(400), "error." + HttpStatus.valueOf(400).value() + ".task-didn't-started");
    }
}