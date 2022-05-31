package com.falpema.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration
public class KafkaConfiguration {
	public Map<String, Object> consumerProperties() {

		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "devs4j-group");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return props;
	}
	
	@Bean
	public ConsumerFactory<String, String> consumerFactory(){
		return new DefaultKafkaConsumerFactory<>(consumerProperties()) ;
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String,String> listenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String,String> listenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
		listenerContainerFactory.setConsumerFactory(consumerFactory());
		return listenerContainerFactory;
		
	}
}