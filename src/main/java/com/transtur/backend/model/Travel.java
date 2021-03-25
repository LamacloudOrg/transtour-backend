package com.transtur.backend.model;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Travel {
	String id;
	String choffer;
	String nombre;
	LocalDate fechaInicio;
	LocalDate fechaFin;
}
