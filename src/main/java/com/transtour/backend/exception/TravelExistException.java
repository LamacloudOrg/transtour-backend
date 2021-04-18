package com.transtour.backend.exception;

public class TravelExistException extends RuntimeException {
    public TravelExistException(String message) {
        super(message);
    }
}
