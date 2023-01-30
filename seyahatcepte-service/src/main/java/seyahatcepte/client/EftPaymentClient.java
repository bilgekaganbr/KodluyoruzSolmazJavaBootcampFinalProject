package seyahatcepte.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "seyahatcepte-payment", url = "http://localhost:8081")
public interface EftPaymentClient {

	@PostMapping(value = "/payment/EFT")
	EftPayment eftPayment(@RequestBody EftPayment eftPayment);
}
