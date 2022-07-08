package com.pedropalma.notification;

import com.pedropalma.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/notification")
@AllArgsConstructor
@Slf4j
public class NotificationController {

	private final NotificationService notificationService;

	public void setNotification(@RequestBody NotificationRequest notificationRequest){
		log.info("New notification... {}", notificationRequest);
		notificationService.send(notificationRequest);
	}

}
