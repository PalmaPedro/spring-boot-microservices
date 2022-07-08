package com.pedropalma.notification;

import com.pedropalma.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {
	private final NotificationRepository notificationRepository;

	public void send(NotificationRequest notificationRequest) {

		notificationRepository.save(
						Notification.builder()
										.toCustomerId(notificationRequest.toCustomerId())
										.toCustomerEmail(notificationRequest.toCustomerEmail())
										.sender("pedropalma")
										.message(notificationRequest.message())
										.sentAt(LocalDateTime.now())
										.build()
		);
	}
}
