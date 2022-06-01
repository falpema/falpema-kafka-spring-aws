package com.falpema.kafka;

import java.util.List;
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

	@KafkaListener(topics = "devs4j-topic",containerFactory="listenerContainerFactory", groupId = "devs4j-group",
			properties= {"max.poll.interval.ms:4000",
					"max.poll.records:10"})
	public void listen(List<String> messages) {
		log.info("start reading messages");
		for (String message : messages) {
			log.info("Message received = {} ", message);
		}
		log.info("Batch complete");
	}

	public static void main(String[] args) {
		SpringApplication.run(FalpemaKafkaSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (int i=0; i<100;i++) {
			kafkaTemplate.send("devs4j-topic",String.format("Sample message %d",i)); 
		}
		
		/*kafkaTemplate.send("devs4j","Sample message ").get(100,TimeUnit.MILLISECONDS); //sincronus */
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
