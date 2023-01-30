package seyahatcepte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication
@EnableFeignClients
@ImportAutoConfiguration({ FeignAutoConfiguration.class })
@EnableEurekaClient
public class SeyahatcepteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeyahatcepteServiceApplication.class, args);
	}

}
