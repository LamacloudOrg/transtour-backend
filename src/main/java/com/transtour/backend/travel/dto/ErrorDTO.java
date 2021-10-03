package com.transtour.backend.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorDTO {

    private LocalDate dateCreated;
    private LocalTime time;
    private String repoName;
    private String stackTrace;
    private String comment;
}
