package com.pedropalma.customer;

import com.pedropalma.clients.fraud.FraudCheckResponse;
import com.pedropalma.clients.fraud.FraudClient;
import com.pedropalma.clients.notification.NotificationClient;
import com.pedropalma.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final FraudClient fraudClient;
	private final NotificationClient notificationClient;
	public void registerCustomer(CustomerRegistrationRequest request) {
		Customer customer = Customer.builder()
						.firstName(request.firstName())
						.lastName(request.lastName())
						.email(request.email())
						.build();
		// todo: check if email is valid
		// todo: check if email not taken
		// todo: check if fraudster
		customerRepository.saveAndFlush(customer);

		FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

		if (fraudCheckResponse.isFraudster()) {
			throw new IllegalStateException("fraudster");
		}
		// todo: make it async (implement queue)
		notificationClient.sendNotification(
						new NotificationRequest(
										customer.getId(),
										customer.getEmail(),
										String.format("Hi %s, Welcome!", customer.getFirstName())
						)
		);
	}
}
