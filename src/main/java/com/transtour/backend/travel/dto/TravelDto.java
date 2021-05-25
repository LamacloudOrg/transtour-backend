package com.transtour.backend.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TravelDto {

	private String orderNumber;
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
