package com.transtour.backend.travel.dto;


import lombok.Data;

@Data
public class MailRequestDTO {

    // Informacion del viaje
    private String origin;
    private String destiny;
    private String driver;
    private String date;
    private String time;
    private String passenger;
    private String observation;
}