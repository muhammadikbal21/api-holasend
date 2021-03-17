package com.enigmacamp.api.holasend.models.excel;

import lombok.Data;

@Data
public class TaskReportModel {

    private String id;

    private String destination;

    private String address;

    private String requestBy;

    private String courier;

    private String pickUpTime;

    private String deliveredTime;

    private String returnTime;

    private String status;

    private String priority;

    private String notes;

    private String createDate;
}
