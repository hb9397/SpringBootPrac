package com.kakao.websocketprac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kakao.websocketprac.service.MailService;
import com.kakao.websocketprac.service.WebPushService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ChatController {
	@GetMapping("/")
	public String chat(){
		return "chatting";
	}

	private final WebPushService webPushService;
	@GetMapping("/push")
	public void push(HttpServletRequest request, HttpServletResponse response) {
		webPushService.push(request, response);
	}

	@GetMapping("/textmail")
	public void textmail(){}

	private final MailService mailService;
	@PostMapping("/textmail")
	public String textmail(HttpServletRequest request) throws Exception{
		mailService.sendMail(request);
		return "redirect:/";
	}
}
