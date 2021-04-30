package com.transtour.backend.travel.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Qualifier("NotificationClient")
@FeignClient(name = "SPRING-CLOUD-NOTIFICATION")
public interface INotification {

    @RequestMapping(method=RequestMethod.POST,value = "/v1/notification/byEmail")
    Void sendNotification(@RequestBody String message);

}
