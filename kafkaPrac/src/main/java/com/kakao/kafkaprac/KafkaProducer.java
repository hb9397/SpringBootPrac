package com.kakao.kafkaprac;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class KafkaProducer {
	// 토픽 이름 설정으로 고유한 값을 가져야한다.
	private static final String TOPIC = "adam-topic";

	private final KafkaTemplate<String, String> kafkaTemplate;

	// 메시지 전송 처리
	public void sendMessage(String message) {
		log.info("Produce message : {}", message);
		this.kafkaTemplate.send(TOPIC, message);
	}

}
