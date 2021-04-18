package com.transtour.backend.exception;

import com.transtour.backend.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class TravelExceptionHandler {

    @ExceptionHandler({ TravelExistException.class })
    public ResponseEntity badRequest(Exception e){
        ResponseDto response = new ResponseDto();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(e.getLocalizedMessage());
        response.setFecha(LocalDateTime.now());
        return new ResponseEntity<ResponseDto>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity notFound(Exception e){
        ResponseDto response = new ResponseDto();
        response.setCode(HttpStatus.NOT_FOUND.value());
        response.setMessage(e.getLocalizedMessage());
        response.setFecha(LocalDateTime.now());
        return new ResponseEntity<ResponseDto>(response,HttpStatus.NOT_FOUND);
    }
}
