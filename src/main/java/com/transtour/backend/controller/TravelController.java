package com.transtour.backend.controller;

import com.transtour.backend.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.transtour.backend.dto.TravelDto;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Function;


@RestController
@RequestMapping(path = "/v1/travel")
public class TravelController {
	
	@Autowired
	TravelService service;
	
	@GetMapping
	public String gretting() {
		return "hello";
	}
	
	@PostMapping("/create")
	public CompletableFuture<ResponseEntity<?>> create(@RequestBody TravelDto travel) throws Exception {
		return service.create(travel).thenApply(handlerTraverlCreation);
	}

	@GetMapping("/{id}")
	public CompletableFuture<ResponseEntity<?>> findById(@PathVariable String id) throws Exception {
		 return service.find(id).thenApply(handlerFinById);
	}

	@GetMapping("/search")
	public ResponseEntity<?> findByDate(@RequestParam(name = "fecha_creacio") LocalDate date ) throws Exception {
		return new ResponseEntity<TravelDto>(HttpStatus.NOT_IMPLEMENTED);

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
