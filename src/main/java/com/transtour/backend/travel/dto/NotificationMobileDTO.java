package com.transtour.backend.travel.dto;

import lombok.Data;

import java.util.Map;

@Data
public class NotificationMobileDTO {

    private String target;
    private String title;
    private String body;
    private Map<String, String> data;
}
