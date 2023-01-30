package seyahatceptePayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SeyahatceptePaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeyahatceptePaymentServiceApplication.class, args);
	}

}
