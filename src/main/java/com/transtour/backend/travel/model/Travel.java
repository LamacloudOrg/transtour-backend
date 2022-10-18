package com.transtour.backend.travel.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@QueryEntity
@Document("travels")
public class Travel implements Serializable {

    @Transient
    public static final String sequenceName = "travel_sequence";

    @Id
    private Long orderNumber;
    private TravelStatus status;
    private LocalDate dateCreated;
    private String car;
    private String carDriver; // Aca va el dni
    private String carDriverName;  // Aca va el nombre del chofer
    private LocalTime time;
    private String company;
    private String bc;
    private String passengerName;
    private String passengerEmail;
    private String reserveNumber;
    private String originAddress;
    private String destinyAddress;
    private String observation;
    private String amount;
    private String whitingTime;
    private String toll;
    private String parkingAmount;
    private String taxForReturn;
    private String totalAmount;

}
