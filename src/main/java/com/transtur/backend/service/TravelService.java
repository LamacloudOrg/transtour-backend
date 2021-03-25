package com.transtur.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dozermapper.core.Mapper;
import com.transtur.backend.dto.TravelDto;
import com.transtur.backend.model.Travel;

@Service
public class TravelService {
	
	@Autowired
	Mapper mapper;
	
	public TravelDto generateTravel() {
		Travel t1 = Travel.builder().nombre("Juan")
				  .id("1")
				  .build();
		TravelDto tDto= new TravelDto();
		mapper.map(t1,tDto);
		return tDto;
	}
}
