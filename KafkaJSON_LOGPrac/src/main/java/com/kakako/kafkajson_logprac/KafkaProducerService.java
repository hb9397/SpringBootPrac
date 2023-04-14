package com.kakako.kafkajson_logprac;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class KafkaProducerService {
	private static final String TOPIC = "testTopic";
	private final KafkaTemplate<String, ChatMessage> kafkaTemplate;

	public void sendMessage(ChatMessage chatmessage) {
		System.out.println("chatmessage = " + chatmessage.getContext());

		kafkaTemplate.send(TOPIC, chatmessage);
	}

}
