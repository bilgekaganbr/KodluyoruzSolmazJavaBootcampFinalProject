package seyahatcepte.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "seyahatcepte-payment", url = "http://localhost:8081")
public interface CardPaymentClient {

	@PostMapping(value = "/payment/creditCard")
	CardPayment cardPayment(@RequestBody CardPayment cardPayment);
}
