package com.transtour.backend.travel.repository;

import com.transtour.backend.travel.dto.MailRequestDTO;
import com.transtour.backend.travel.dto.TravelNotificationMobileDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Qualifier("NotificationClient")
@FeignClient(name = "SPRING-CLOUD-NOTIFICATION-API")
public interface INotification {

    @RequestMapping(method = RequestMethod.POST, value = "/v1/notification/sendMessageMobile")
    Void sendNotificationMobile(@RequestBody TravelNotificationMobileDTO travelNotificationMobileDto);

    @RequestMapping(method = RequestMethod.POST, value = "/v1/notification/sendingEmail")
    Void sendNotificationV2(@RequestBody MailRequestDTO mailRequestDTO);
}
