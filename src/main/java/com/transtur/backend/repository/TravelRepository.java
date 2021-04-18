package com.transtur.backend.repository;

import com.transtur.backend.model.Travel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelRepository  extends MongoRepository<Travel, String> , QuerydslPredicateExecutor<Travel> {
    //Page<Travel> findByFechaInicio(LocalDate fecha, Pageable pageable);

    @Query("{ 'age' : { $gt: ?0, $lt: ?1 } }")
    List<Travel> findUsersByAgeBetween(int ageGT, int ageLT);
}
