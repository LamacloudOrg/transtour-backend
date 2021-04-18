package com.transtur.backend.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.querydsl.core.annotations.QueryEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@NoArgsConstructor
@QueryEntity
@Document(collection = "travel")
public class Travel {
	@Id
	String id;
	String chofer;
	String pasajero;
	LocalDate fecha;
	LocalTime hora;
}
