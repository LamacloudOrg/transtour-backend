package com.transtour.backend.travel.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@Data
@QueryEntity
@Document("travel")
public class Travel implements Serializable {
	@Id
	String id;
	String chofer;
	String pasajero;
	LocalDate fecha;
	LocalTime hora;
	String direccion;
	@CreatedDate
	LocalDateTime crate_at;
}
