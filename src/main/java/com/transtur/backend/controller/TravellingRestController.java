package com.transtur.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transtur.backend.dto.TravelDto;
import com.transtur.backend.service.TravelService;

@RestController
@RequestMapping(path = "/travell")
public class TravellingRestController {
	
	@Autowired
	TravelService service;
	
	@GetMapping
	public String gretting() {
		return "hello";
	}
	
	@GetMapping("/create")
	public ResponseEntity<?> create() {
		return  new ResponseEntity<TravelDto>(service.generateTravel(), HttpStatus.ACCEPTED); 
	}
	
	
	

}
