package com.kakako.kafkajson_logprac;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/kafka")
@Slf4j
@RequiredArgsConstructor

public class KafkaController {
	private final KafkaProducerService producerService;


	@PostMapping
	@ResponseBody
	public String sendMessage(@RequestBody ChatMessage chatmessage) {
		log.warn("msg: {}", chatmessage.getContext());
		producerService.sendMessage(chatmessage);
		return "success";
	}

}
