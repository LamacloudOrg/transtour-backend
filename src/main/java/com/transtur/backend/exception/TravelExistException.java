package com.transtur.backend.exception;

public class TravelExistException extends RuntimeException {
    public TravelExistException(String message) {
        super(message);
    }
}
