package com.enigmacamp.api.holasend.models;

import lombok.Data;

@Data
public class FirebaseNotification {
    private final String to = "/topics/NewTask";
    private Notification notification = new Notification();
    private NotificationData data = new NotificationData();
}

