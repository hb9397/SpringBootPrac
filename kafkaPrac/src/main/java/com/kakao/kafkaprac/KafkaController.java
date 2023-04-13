package com.kakao.kafkaprac;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/kafka")
@Log4j2
@RequiredArgsConstructor
public class KafkaController {
	private final KafkaProducer producer;

	@PostMapping
	@ResponseBody
	public String sendMessage(@RequestParam String message) {
		log.info("message : {}", message);
		this.producer.sendMessage(message);
		return "success";
	}

}
