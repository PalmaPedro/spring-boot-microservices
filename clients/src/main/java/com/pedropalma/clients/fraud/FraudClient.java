package com.pedropalma.clients.fraud;

@FeignClient(
				name = "fraud",
				url = "${clients.fraud.url}"
)
public interface FraudClient {

	@GetMapping(path = "api/v1/fraud-check/{customerId}")
	FraudCheckResponse isFraudster(
					@PathVariable("customerId") Integer customerId);

}
