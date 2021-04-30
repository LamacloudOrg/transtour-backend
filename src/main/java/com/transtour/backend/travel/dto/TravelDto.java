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
	private String id;
	private String chofer;
	private String pasajero;
	private LocalDate fecha;
	private LocalTime hora;
}
