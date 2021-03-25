package com.transtur.backend.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TravelDto {
	String id;
	String choffer;
	String nombre;
	LocalDate fechaInicio;
	LocalDate fechaFin;
}
