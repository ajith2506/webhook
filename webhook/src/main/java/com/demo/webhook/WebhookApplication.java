 package com.demo.webhook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebhookApplication {
	public static void main(String[] args) {
		Thread.currentThread().setName("WB-RECEIVER");
		SpringApplication.run(WebhookApplication.class, args);
	}

}
