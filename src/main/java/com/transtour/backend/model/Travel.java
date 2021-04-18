package com.transtour.backend.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.querydsl.core.annotations.QueryEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

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
	@CreatedDate
	LocalDateTime crate_at;
}
