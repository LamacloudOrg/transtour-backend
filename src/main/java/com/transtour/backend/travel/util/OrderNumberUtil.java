package com.transtour.backend.travel.util;

import com.transtour.backend.travel.model.OrderNumber;
import com.transtour.backend.travel.repository.IOrderNumberRepo;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class OrderNumberUtil implements DisposableBean {


    private Long actualNumber = 0l;

    @Qualifier("OrderNumberRepo")
    @Autowired
    private IOrderNumberRepo orderRepo;

    private OrderNumber orderNumber = null;

    @PostConstruct
    private void init() {
        Optional<OrderNumber> orderNumber_ = orderRepo.findAll().stream().findFirst();
        this.orderNumber = orderNumber_.get();
        this.actualNumber = this.orderNumber.getNumber();
    }

    public synchronized Long getNumber() {
        return ++actualNumber;
    }

    @Override
    public void destroy() throws Exception {
        this.orderNumber.setNumber(actualNumber);
        orderRepo.save(this.orderNumber);
    }


}
