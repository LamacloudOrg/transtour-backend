package com.transtour.backend.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TravelDto {

	@Null
	private Long orderNumber;
	@NotNull
	private LocalDate dateCreated;
	@NotNull
	@NotEmpty
	private String car;
	@NotNull
	@NotEmpty
	private String carDriver; // Aca va el DNI
	@NotNull
	@NotEmpty
	private String carDriverName;  // Aca va el nombre del chofer
	@NotNull
	private LocalTime time;
	@NotNull
	@NotEmpty
	private String company;
	@NotNull
	@NotEmpty
	private String bc;
	@NotNull
	@NotEmpty
	private String passenger;
	@NotNull
	@NotEmpty
	private String reserveNumber;
	@NotNull
	@NotEmpty
	private String originAddress;
	@NotNull
	@NotEmpty
	private String destinyAddress;
	@NotNull
	private String observation;
	@NotNull
	@NotEmpty
	private String amount;
	@NotNull
	@NotEmpty
	private String whitingTime;
	@NotNull
	@NotEmpty
	private String toll;
	@NotNull
	@NotEmpty
	private String parkingAmount;
	@NotNull
	@NotEmpty
	private String taxForReturn;
	@NotNull
	@NotEmpty
	private String totalAmount;
}
