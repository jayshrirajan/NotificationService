package com.msys.digitalwallet.notification;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.msys.digitalwallet"})
@EnableAutoConfiguration
@OpenAPIDefinition(info = @Info(title = "Digital Wallet Notification APIS",
		version = "0.1",
		description = "Digital Wallet Notification Microservice"))
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

}
