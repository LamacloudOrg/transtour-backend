package com.transtour.backend.travel.exception;

public class InputsRequiredException   extends RuntimeException{
    public InputsRequiredException(String message) {
        super(message);
    }

}
