package com.transtour.backend.service;

import com.querydsl.core.types.Predicate;
import com.transtour.backend.exception.NotFoundException;
import com.transtour.backend.exception.TravelExistException;
import com.transtour.backend.model.QTravel;
import com.transtour.backend.repository.INotification;
import com.transtour.backend.repository.TravelRepository;
import net.bytebuddy.implementation.Implementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.transtour.backend.dto.TravelDto;
import com.transtour.backend.model.Travel;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class TravelService {

	@Autowired
	private Mapper mapper;

	@Autowired
	@Qualifier("repo")
	private TravelRepository repository;

	@Autowired
	@Qualifier("NotificationClient")
	private INotification notificationClient;

	public CompletableFuture<Object> create(TravelDto travelDto) throws Exception {
		CompletableFuture<String> completableFuture  = CompletableFuture.supplyAsync( ()-> {

			QTravel travel = new QTravel("travel");
			Predicate dateAndHour = travel.fecha.eq(travelDto.getFecha()).and(travel.hora.eq(travelDto.getHora()));

			Optional<Travel> travelExist = repository.findOne(dateAndHour);
			if (travelExist.isPresent()) throw  new TravelExistException("el viaje ya existe");
			Travel newTravel = mapper.map(travelDto,Travel.class);
			repository.save(newTravel);

			return "se creo el viaje para el cofher "+travelDto.getChofer();

		});

		CompletableFuture<Object> notified = completableFuture.thenApply(s-> sendNotification(s));

		return notified;
	}

	public CompletableFuture<TravelDto> find(String id) throws Exception {
		CompletableFuture<TravelDto> completableFuture  = CompletableFuture.supplyAsync( ()-> {

			QTravel travel = new QTravel("travel");
			Predicate byId = travel.id.eq(id);
			Optional<Travel> travelExist = repository.findOne(byId);
			if(!travelExist.isPresent()) throw  new NotFoundException("no existe el viaje");
			TravelDto travelDTO = mapper.map(travelExist.get(),TravelDto.class);
			return travelDTO;
		});

		return completableFuture;
	}

	public CompletableFuture<Void> sendNotification(String message){

		CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(
				()->{
					notificationClient.sendNotification(message);
				}
		);

		return completableFuture;

	}

}
