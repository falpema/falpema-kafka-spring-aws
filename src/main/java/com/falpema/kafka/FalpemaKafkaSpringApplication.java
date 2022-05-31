package com.falpema.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class FalpemaKafkaSpringApplication {
	
	private static final Logger log = LoggerFactory.getLogger(FalpemaKafkaSpringApplication.class);
	
	@KafkaListener(topics = "falpema-topic", groupId= "falpema-group")
	public void listen(String message) {
		log.info("Message received",message);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(FalpemaKafkaSpringApplication.class, args);
	}

}
