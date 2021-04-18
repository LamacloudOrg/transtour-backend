package com.transtour.backend.controller;

import com.transtour.backend.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.transtour.backend.dto.TravelDto;
import java.time.LocalDate;
import java.util.concurrent.Future;


@RestController
@RequestMapping(path = "/v1/travel")
public class TravellingRestController {
	
	@Autowired
	TravelService service;
	
	@GetMapping
	public String gretting() {
		return "hello";
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody TravelDto travel) throws Exception {

		Future<Void> future = service.create(travel);
		future.get();
		return new ResponseEntity<TravelDto>(HttpStatus.ACCEPTED);

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable String id) throws Exception {
		Future<TravelDto> future = service.find(id);
		TravelDto travel = future.get();
		return new ResponseEntity<TravelDto>(travel,HttpStatus.OK);

	}
	@GetMapping("/search")
	public ResponseEntity<?> findByDate(@RequestParam(name = "fecha_creacio") LocalDate date ) throws Exception {
		return new ResponseEntity<TravelDto>(HttpStatus.NOT_IMPLEMENTED);

	}

	

}
