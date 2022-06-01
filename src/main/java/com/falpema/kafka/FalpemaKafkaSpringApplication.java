package com.falpema.kafka;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

@SpringBootApplication
public class FalpemaKafkaSpringApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(FalpemaKafkaSpringApplication.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@KafkaListener(topics = "devs4j-topic", groupId = "devs4j-group")
	public void listen(String message) {
		log.info("Message received {} ", message);
	}

	public static void main(String[] args) {
		SpringApplication.run(FalpemaKafkaSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		kafkaTemplate.send("devs4j","Sample message ").get(100,TimeUnit.MILLISECONDS);
		/*ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("devs4j-topic", "Sample message");
		future.addCallback(new KafkaSendCallback<String, String>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				log.info("Message sent {}", result.getRecordMetadata().offset());

			}

			@Override
			public void onFailure(Throwable ex) {
				log.error("Error sending message", ex);

			}

			@Override
			public void onFailure(KafkaProducerException ex) {
				log.error("Error sending message ", ex);

			}
		});*/

	}

}
