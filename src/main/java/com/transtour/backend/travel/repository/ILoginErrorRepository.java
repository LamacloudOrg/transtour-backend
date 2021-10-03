package com.transtour.backend.travel.repository;

import com.transtour.backend.travel.model.LoginError;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("repoError")
public interface ILoginErrorRepository extends MongoRepository<LoginError, String>, QuerydslPredicateExecutor<LoginError> {

}
