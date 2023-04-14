package com.kakao.kafkajson_log_consumer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
	private String sender;
	private String context;
}
