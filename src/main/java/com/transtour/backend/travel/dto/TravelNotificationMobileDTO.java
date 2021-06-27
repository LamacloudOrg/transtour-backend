package com.transtour.backend.travel.dto;

import lombok.Data;

import java.util.Map;

@Data
public class TravelNotificationMobileDTO {

    private String to;
    private Map<String, String> notification;
    private Map<String, String> data;
}
