package com.transtour.backend.travel.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Data
@QueryEntity
@Document("travel")
public class Travel implements Serializable {

	private String orderNumber;
	private TravelStatus status;
	private LocalDate dateCreated;
	private String car;
	private String carDriver;
	private LocalTime time;
	private String company;
	private String bc;
	private String passenger;
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
