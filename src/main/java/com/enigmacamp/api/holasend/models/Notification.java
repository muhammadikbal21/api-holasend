package com.enigmacamp.api.holasend.models;

import lombok.Data;

@Data
public class Notification {
    String body = "great match!";
    Boolean content_available = true;
    String priority = "high";
    String title = "You Have A New Task";
    String message = "great match!";
}
