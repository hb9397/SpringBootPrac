package com.kakao.kafkajson_log_consumer;

import org.springframework.kafka.annotation.KafkaListener;

public class KafkaConsumerService {
	@KafkaListener(topics = "testTopic", groupId = "testgroup", containerFactory = "kafkaListener")
	public void consume(ChatMessage message){
		System.out.println("name = " + message.getSender());
		System.out.println("consume message = " + message.getContext());
	}
}
