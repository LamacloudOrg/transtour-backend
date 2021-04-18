package com.transtur.backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TravelDto {
	private String id;
	private String choffer;
	private String pasajero;
	private LocalDate fecha;
	private LocalTime hour;
}
