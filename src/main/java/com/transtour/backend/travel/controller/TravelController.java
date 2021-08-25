package com.transtour.backend.travel.controller;

import com.transtour.backend.travel.dto.TravelDto;
import com.transtour.backend.travel.dto.SaveTaxesDTO;
import com.transtour.backend.travel.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;


@RestController
@RequestMapping(path = "/v1/travel")
@CrossOrigin("*")
public class TravelController {
	
	@Autowired
	TravelService service;
	
	@GetMapping
	public String gretting() {
		return "hello";
	}


	@GetMapping("/orederNumber")
	public CompletableFuture<ResponseEntity> getOrderNumber() throws Exception {
		return service.generateNumber().thenApply(handlerFinById);
	}


	@PostMapping("/create")
	public CompletableFuture<ResponseEntity> create(@RequestBody TravelDto travel) throws Exception {
		return service.create(travel).thenApply(handlerTraverlCreation);
	}

	//TODO Implementar generacion de orderNumber, poible solucion con un bean de session.

	@PostMapping("/aprove")
	public CompletableFuture<ResponseEntity> aprove(@RequestBody String orderNumber) throws Exception {
		return service.aprove(orderNumber).thenApply(handlerTraverlCreation);
	}

	@GetMapping("/{id}")
	public CompletableFuture<ResponseEntity> findById(@PathVariable String id) throws Exception {
		 return service.find(id).thenApply(handlerFinById);
	}

	@GetMapping("/search")
	public ResponseEntity<?> findByDate(@RequestParam(name = "fecha_creacio") LocalDate date ) throws Exception {
		return new ResponseEntity<TravelDto>(HttpStatus.NOT_IMPLEMENTED);

	}

	@GetMapping("/list")
	public CompletableFuture<ResponseEntity> list (Pageable pageable) throws Exception {
		return service.findAll(pageable).thenApply(handlerFinById);

	}

	@PostMapping("/saveTaxes")
	public CompletableFuture<ResponseEntity> saveTaxes(@RequestBody SaveTaxesDTO saveTaxesDTO) throws Exception {
		return service.saveTaxes(saveTaxesDTO).thenApply(handlerTraverlCreation);
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
