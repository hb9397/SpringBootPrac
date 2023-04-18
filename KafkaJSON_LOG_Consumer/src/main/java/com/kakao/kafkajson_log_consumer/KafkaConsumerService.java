package com.kakao.kafkajson_log_consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class KafkaConsumerService {
	@KafkaListener(topics = "beom-topic",groupId = "beom", containerFactory = "kafkaListener")
	public void consume(ChatMessage message){
		System.out.println("name = " + message.getSender());
		//System.out.println("consume message = " + message.getContext());
		log.warn("msg: {}, from: {}", message.getContext(), message.getSender());

	}
}
