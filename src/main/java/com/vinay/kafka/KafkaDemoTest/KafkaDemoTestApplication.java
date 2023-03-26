package com.vinay.kafka.KafkaDemoTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.vinay.kafka.KafkaDemoTest")
public class KafkaDemoTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaDemoTestApplication.class, args);
	}

}
