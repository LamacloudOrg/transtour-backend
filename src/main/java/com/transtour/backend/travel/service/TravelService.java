package com.transtour.backend.travel.service;

import com.github.dozermapper.core.Mapper;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.querydsl.core.types.Predicate;
import com.transtour.backend.travel.dto.TravelDto;
import com.transtour.backend.travel.exception.NotFoundException;
import com.transtour.backend.travel.exception.TravelExistException;
import com.transtour.backend.travel.model.QTravel;
import com.transtour.backend.travel.model.Travel;
import com.transtour.backend.travel.repository.INotification;
import com.transtour.backend.travel.repository.TravelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class TravelService {

	private static Logger log = LoggerFactory.getLogger(TravelService.class);

	@Autowired
	private Mapper mapper;

	@Autowired
	@Qualifier("repo")
	private TravelRepository repository;

	@Autowired
	@Qualifier("NotificationClient")
	private INotification notificationClient;

	public CompletableFuture<Object> create(TravelDto travelDto) throws Exception {
		CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
			return createTravel(travelDto);
		});

		CompletableFuture<Object> notified = completableFuture.thenApply(s-> sendNotification(s));
		return notified;
	}

	@Transactional
	private String createTravel(TravelDto travelDto){
		QTravel travel = new QTravel("travel");
		Predicate dateAndHour = travel.fecha.eq(travelDto.getFecha()).and(travel.hora.eq(travelDto.getHora()));

		Optional<Travel> travelExist = repository.findOne(dateAndHour);
		if (travelExist.isPresent()) throw new TravelExistException("el viaje ya existe");
		Travel newTravel = mapper.map(travelDto, Travel.class);
		repository.save(newTravel);

		return newTravel.getId() + "-" + "se creo el viaje para el cofher " + travelDto.getChofer();
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

	//@HystrixCommand(fallbackMethod = "notificationError",commandKey="test")
	public CompletableFuture<Void> sendNotification(String message){

		CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(
				()->{
					notificationClient.sendNotification(message.split("-")[1]);			}
		);
		return completableFuture;
	}

	public Future<Void> notificationError(String message){

		Future<Void> completableFuture = CompletableFuture.runAsync(
				()->{
					log.error("No se pudo notificar el viaje "+message.split("-")[0]);	}
		);
		return completableFuture;
	}

}
