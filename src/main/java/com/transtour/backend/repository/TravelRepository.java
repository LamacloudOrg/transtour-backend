package com.transtour.backend.repository;

import com.transtour.backend.model.Travel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("repo")
public interface TravelRepository  extends MongoRepository<Travel, String> , QuerydslPredicateExecutor<Travel> {
    //Page<Travel> findByFechaInicio(LocalDate fecha, Pageable pageable);

    @Query("{ 'age' : { $gt: ?0, $lt: ?1 } }")
    List<Travel> findUsersByAgeBetween(int ageGT, int ageLT);
}
