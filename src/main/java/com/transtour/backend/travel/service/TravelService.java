package com.transtour.backend.travel.service;

import com.github.dozermapper.core.Mapper;
import com.querydsl.core.types.Predicate;
import com.transtour.backend.travel.dto.MailRequestDTO;
import com.transtour.backend.travel.dto.TravelNotificationMobileDTO;
import com.transtour.backend.travel.dto.TravelDto;
import com.transtour.backend.travel.dto.SaveTaxesDTO;
import com.transtour.backend.travel.exception.NotFoundException;
import com.transtour.backend.travel.exception.TravelExistException;
import com.transtour.backend.travel.model.QTravel;
import com.transtour.backend.travel.model.Travel;
import com.transtour.backend.travel.model.TravelStatus;
import com.transtour.backend.travel.repository.INotification;
import com.transtour.backend.travel.repository.IVoucher;
import com.transtour.backend.travel.repository.TravelRepository;
import com.transtour.backend.travel.util.Constants;
import com.transtour.backend.travel.util.OrderNumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

	@Autowired
	@Qualifier("VoucherClient")
	private IVoucher voucher;

	@Autowired
	OrderNumberUtil orderNumberUtil;

	public CompletableFuture<Long> generateNumber(){
		CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(() -> {
			return orderNumberUtil.getNumber();
		});
		return completableFuture;
	}

	public CompletableFuture<Object> create(TravelDto travelDto) throws Exception {
		CompletableFuture<Travel> completableFuture = CompletableFuture.supplyAsync(() -> {
			return createTravel(travelDto);
		});

		CompletableFuture<Object> notified = completableFuture.thenApply(s-> sendNotificationV2(s));
		return notified;
	}

	@Transactional
	private Travel createTravel(TravelDto travelDto){
		QTravel travel = new QTravel("travel");


		//TODO ojo la logica de negocio, puede haber mas de un viaje para un mismo lugar de origen, destino, fecha, hoora, puede ser un contingente.
		Predicate notExistTravel = travel.dateCreated.eq(travelDto.getDateCreated())
				.and(travel.time.eq(travelDto.getTime()))
				.and(travel.carDriver.eq(travelDto.getCarDriver()))
				.and(travel.destinyAddress.eq(travelDto.getDestinyAddress()))
				.and(travel.originAddress.eq(travelDto.getOriginAddress()));

		Optional<Travel> travelExist = repository.findOne(notExistTravel);
		if (travelExist.isPresent()) throw new TravelExistException("el viaje ya existe viaje para esa fecha y hora con el mismo origen y destino para el cofer" +travelDto.getCarDriverName());
		Travel newTravel = mapper.map(travelDto, Travel.class);

		Predicate findByOrderNumber = travel.orderNumber.eq(travelDto.getOrderNumber());
		Optional<Travel> isEditiom = repository.findOne(findByOrderNumber);
		isEditiom.ifPresent(
				(travel1)->{
					newTravel.setId(travel1.getId());
				}
		);
		newTravel.setStatus(TravelStatus.CREATED);
		repository.save(newTravel);

		return newTravel;
	}

	public CompletableFuture<TravelDto> find(String orderNumber) throws Exception {
		CompletableFuture<TravelDto> completableFuture  = CompletableFuture.supplyAsync( ()-> {

			QTravel travel = new QTravel("travel");
			Predicate byOrderNumber = travel.orderNumber.eq(orderNumber);
			Optional<Travel> travelExist = repository.findOne(byOrderNumber);
			if(!travelExist.isPresent()) throw  new NotFoundException("no existe el viaje");
			TravelDto travelDTO = mapper.map(travelExist.get(),TravelDto.class);
			return travelDTO;
		});

		return completableFuture;
	}

	//@HystrixCommand(fallbackMethod = "notificationError",commandKey="test")
	public CompletableFuture<Void> sendNotificationV2(Travel travel){

		CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(
				()->{
					notificationClient.sendNotificationV2(mapperEmail(travel));			}
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


	public CompletableFuture<Page<Travel>> findAll(Pageable page){

		CompletableFuture<Page<Travel>> completableFuture  = CompletableFuture.supplyAsync( ()-> {
			Page<Travel> list = repository.findAll(page);
			return list;
		});

		return completableFuture;
	}

	public CompletableFuture<Travel> aprove(String orderNumber) {

		CompletableFuture<Travel> cf1 = CompletableFuture.supplyAsync(() -> {
			QTravel travel = new QTravel("travel");
			Predicate byOrderNumber = travel.orderNumber.eq(orderNumber).and(travel.status.eq(TravelStatus.CREATED));
			Optional<Travel> travelExist = repository.findOne(byOrderNumber);
			if(!travelExist.isPresent()) throw  new NotFoundException("El viaje no fue creado");
			travelExist.get().setStatus(TravelStatus.APROVED);
			repository.save(travelExist.get());
			return travelExist.get();
			});

		CompletableFuture<Travel> cf2 = cf1.thenApply(travel->{
				 this.creteVoucer(travel);
				return travel;
		});

		CompletableFuture<Travel> cf3 = cf2.thenApply(travel->{
			 this.sendNotification(travel);
			 return travel;
		});

		return cf3;

	}

	private CompletableFuture<Void> creteVoucer(Travel travel){

		CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(
				()->{
					voucher.createVoucher(travel);
					//return travel;
				}
		);
		return completableFuture;
	}

	private CompletableFuture<Void> sendNotification(Travel travel){
		CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(
				()->{
					TravelNotificationMobileDTO travelNotificationMobileDTO = new TravelNotificationMobileDTO();
					travelNotificationMobileDTO.setTo("");

					Map<String, String> notification = new HashMap<>();
					notification.put(Constants.TITTLE, Constants.TITTLE_NEW_MESSAGE);
					notification.put(Constants.BODY, Constants.BODY_NEW_MESSAGE);

					Map<String, String> data = new HashMap<>();
					data.put(Constants.ID, travel.getOrderNumber());
					data.put(Constants.ORIGIN, travel.getOriginAddress());
					data.put(Constants.DESTINY, travel.getDestinyAddress());
					data.put(Constants.TIME, travel.getTime().toString());
					data.put(Constants.DATE, travel.getDateCreated().toString());
					data.put(Constants.PASSENGER, travel.getPassenger());
					data.put(Constants.OBSERVATION, travel.getObservation());
					data.put(Constants.CAR_DRIVER, travel.getCarDriver());

					data.put(Constants.COMPANY, travel.getCompany());
					data.put(Constants.NET_AMOUNT, travel.getAmount());
					data.put(Constants.WAITING_TIME, travel.getWhitingTime());
					data.put(Constants.TOLL, travel.getToll());
					data.put(Constants.PARKING_AMOUNT, travel.getParkingAmount());
					data.put(Constants.TAX_FOR_RETURN, travel.getTaxForReturn());

					travelNotificationMobileDTO.setNotification(notification);
					travelNotificationMobileDTO.setData(data);

					notificationClient.sendNotificationMobile(travelNotificationMobileDTO);
					//return travel;
				}
		);
		return completableFuture;
	}


	public CompletableFuture<Travel> saveTaxes (SaveTaxesDTO saveTaxesDTO) {

		CompletableFuture<Travel> cf1 = CompletableFuture.supplyAsync(() -> {
			QTravel travel = new QTravel("travel");
			Predicate byOrderNumber = travel.orderNumber.eq(saveTaxesDTO.getOrderNumber()).and(travel.status.eq(TravelStatus.APROVED));
			Optional<Travel> travelExist = repository.findOne(byOrderNumber);
			if(!travelExist.isPresent()) throw  new NotFoundException("El viaje no fue aprobado");
			travelExist.get().setWhitingTime(saveTaxesDTO.getWhitingTime());
			travelExist.get().setToll(saveTaxesDTO.getToll());
			travelExist.get().setParkingAmount(saveTaxesDTO.getParkingAmount());
			travelExist.get().setTaxForReturn(saveTaxesDTO.getTaxForReturn());
			repository.save(travelExist.get());
			return travelExist.get();
		});

		return cf1;
	}

	public MailRequestDTO mapperEmail (Travel travel) {
		MailRequestDTO mail = new MailRequestDTO();
		mail.setSignature("TransTour");
		mail.setLocation("Capital Federal");
		mail.setTo("cnlaffitte@gmail.com ; pomalianni@gmail.com");
		mail.setFrom("pomalianni@gmail.com");

		mail.setOrigin(travel.getOriginAddress());
		mail.setDestiny(travel.getDestinyAddress());
		mail.setDriver(travel.getCarDriver());
		mail.setDate(travel.getDateCreated().toString());
		mail.setTime(travel.getTime().toString());
		mail.setPassenger(travel.getPassenger());
		mail.setObservation(travel.getObservation());
		return mail;
	}
}
