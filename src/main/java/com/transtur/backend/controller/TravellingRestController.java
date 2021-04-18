package com.transtur.backend.controller;

import com.transtur.backend.model.Travel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.transtur.backend.dto.TravelDto;
import com.transtur.backend.service.TravelService;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(path = "/v1/travel")
public class TravellingRestController {
	
	@Autowired
	TravelService service;
	
	@GetMapping
	public String gretting() {
		return "hello";
	}
	
	@GetMapping("/create")
	public ResponseEntity<?> create(@RequestBody TravelDto travel) throws ExecutionException, InterruptedException {
		service.create(travel);
		return  new ResponseEntity<TravelDto>(HttpStatus.ACCEPTED);
	}
	
	
	

}
