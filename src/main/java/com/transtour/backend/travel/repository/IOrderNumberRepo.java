package com.transtour.backend.travel.repository;

import com.transtour.backend.travel.model.OrderNumber;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Qualifier("OrderNumberRepo")
@Repository
public interface IOrderNumberRepo extends MongoRepository<OrderNumber, String> {
}
