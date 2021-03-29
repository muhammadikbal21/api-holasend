package com.enigmacamp.api.holasend.exceptions;

import org.springframework.http.HttpStatus;

public class TaskProceseedException extends ApplicationException {

    public TaskProceseedException() {
        super(HttpStatus.valueOf(400), "error." + HttpStatus.valueOf(400).value() + ".task-processed");
    }
}