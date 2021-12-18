package com.transtour.backend.travel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;

public class AbstractHandler {
    protected static Function<Object, ResponseEntity> handlerTraverlCreation = t -> {
        //log.error(String.format("Unable to retrieve user for id: %s", userId), throwable);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    };


    protected static Function<Object, ResponseEntity> handlerFinById = t -> {
        //log.error(String.format("Unable to retrieve user for id: %s", userId), throwable);
        return ResponseEntity.ok().body(t);
    };
}
