package com.transtur.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transtur.backend.model.Travel;

public interface TravelRepository  extends JpaRepository<Travel, Long>{

}
