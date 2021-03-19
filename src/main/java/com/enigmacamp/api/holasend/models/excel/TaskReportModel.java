package com.enigmacamp.api.holasend.models.excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskReportModel {

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
