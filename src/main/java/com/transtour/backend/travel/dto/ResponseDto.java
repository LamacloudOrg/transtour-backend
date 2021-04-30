package com.transtour.backend.travel.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@Data
public class ResponseDto {
    Integer code;
    String message;
    LocalDateTime fecha;
}
