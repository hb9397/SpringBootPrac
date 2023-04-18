package com.kakako.kafkajson_logprac;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class KafkaProducerService {
	private static final String TOPIC = "beom-topic";
	private final KafkaTemplate<String, ChatMessage> kafkaTemplate;

	public void sendMessage(ChatMessage chatmessage) {
		//System.out.println("chatmessage = " + chatmessage.getContext());
		log.warn("TOPIC: {}, msg: {}", TOPIC, chatmessage.getContext());
		kafkaTemplate.send(TOPIC, chatmessage);
	}

}
