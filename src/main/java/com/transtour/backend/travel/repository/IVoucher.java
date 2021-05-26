package com.transtour.backend.travel.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Qualifier("VoucherClient")
@FeignClient(name = "SPRING-CLOUD-VOUCHER-API")
public interface IVoucher {

    @RequestMapping(method= RequestMethod.POST,value = "/v1/voucher/create")
    Void createVoucher(@RequestBody String travelId);
}
