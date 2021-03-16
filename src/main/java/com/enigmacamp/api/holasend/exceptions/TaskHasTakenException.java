package com.enigmacamp.api.holasend.exceptions;

import org.springframework.http.HttpStatus;

public class TaskHasTakenException extends ApplicationException {

    public TaskHasTakenException() {
        super(HttpStatus.valueOf(400), "error." + HttpStatus.valueOf(400).value() + ".task-has-taken");
    }
}