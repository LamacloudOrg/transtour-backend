package com.transtour.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.HttpStatus;

import java.time.LocalDateTime;
@NoArgsConstructor
@Data
public class ResponseDto {
    Integer code;
    String message;
    LocalDateTime fecha;
}
