package com.kakao.websocketprac.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	private final JavaMailSender javaMailSender;

	public void sendMail(HttpServletRequest request) {
		String setfrom = "hb19971127@gmail.com";
		String tomail = request.getParameter("tomail"); // 받는 사람 이메일
		String title = request.getParameter("title"); // 보내는 사람 이메일
		String content = request.getParameter("content"); // 보내는 사람 이메일
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(setfrom); // 보내는사람 생략하거나 하면 정상작동을 안함
			message.setTo(tomail); // 받는사람 이메일
			message.setSubject(title); // 메일제목은 생략이 가능하다
			message.setText(content); // 메일 내용
			javaMailSender.send(message);
		} catch(Exception e){
			System.out.println(e);
		}
	}
}