package com.pedropalma.customer;

import com.pedropalma.amqp.RabbitMQMessageProducer;
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
	private final RabbitMQMessageProducer rabbitMQMessageProducer;
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

		NotificationRequest notificationRequest = new NotificationRequest(
						customer.getId(),
						customer.getEmail(),
						String.format("Hi %s, welcome...", customer.getFirstName())
		);
		rabbitMQMessageProducer.publish(
						notificationRequest,
						"internal.exchange",
						"internal.notification.routing-key"
		);
	}
}
