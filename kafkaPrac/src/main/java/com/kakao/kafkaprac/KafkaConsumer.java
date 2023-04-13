package com.kakao.kafkaprac;

import java.io.IOException;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class KafkaConsumer {
	@KafkaListener(topics = "adam-topic", groupId = "adamsoft")
	public void consume(String message) throws IOException {
		log.info("Consumed message : {}", message);
	}

}
