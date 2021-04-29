package com.transtour.backend.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Qualifier("NotificationClient")
@FeignClient(name = "service-notfication")
public interface INotification {

    @RequestMapping(method=RequestMethod.POST,value = "/v1/notification/byEmail")
    Void sendNotification(@RequestBody String message);

}
