package com.transtur.backend.service;

import com.querydsl.core.types.Predicate;
import com.transtur.backend.exception.TravelExistException;
import com.transtur.backend.model.QTravel;
import com.transtur.backend.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.transtur.backend.dto.TravelDto;
import com.transtur.backend.model.Travel;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class TravelService {

	@Autowired
	Mapper mapper;

	@Autowired
	TravelRepository repository;

	public CompletableFuture<Void> create(TravelDto travelDto) throws ExecutionException, InterruptedException {
		CompletableFuture<Void> completableFuture  = CompletableFuture.runAsync( ()-> {

			QTravel travel = new QTravel("travel");
			Predicate dateAndHour = travel.fecha.eq(travelDto.getFecha()).and(travel.hora.eq(travelDto.getHour()));

			Optional<Travel> travelExist = repository.findOne(dateAndHour);
			if (travelExist.isPresent()) throw  new TravelExistException("el viaje ya existe");
			Travel newTravel = mapper.map(travelDto,Travel.class);
			repository.save(newTravel);

		});
		return completableFuture;
	}
}
