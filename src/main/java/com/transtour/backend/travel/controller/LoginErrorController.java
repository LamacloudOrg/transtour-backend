package com.transtour.backend.travel.controller;

import com.transtour.backend.travel.dto.ErrorDTO;
import com.transtour.backend.travel.service.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@RestController
@RequestMapping(path = "/v1/LoginError")
@CrossOrigin("*")
public class LoginErrorController {

    @Autowired
    ErrorService service;

    @PostMapping("/insertError")
    public CompletableFuture<ResponseEntity> insert (@RequestBody ErrorDTO error) throws Exception {
        return service.insert(error).thenApply(handlerTraverlCreation);
    }
    private static Function<Object,ResponseEntity> handlerTraverlCreation = s->{
        //log.error(String.format("Unable to retrieve user for id: %s", userId), throwable);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    };

    private static Function<Object,ResponseEntity> handlerFinById = t->{
        //log.error(String.format("Unable to retrieve user for id: %s", userId), throwable);
        return ResponseEntity.ok().body(t);
    };
}
