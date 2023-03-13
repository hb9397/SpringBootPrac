package com.kakao.websocketprac.service;

import java.io.PrintWriter;
import java.util.Random;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class WebPushService {
	public void push(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			response.setContentType("text/event-stream");
			response.setCharacterEncoding("UTF-8");
			writer = response.getWriter();
			Random r = new Random();
			writer.write("data: " + (r.nextInt(46)+1) + "\n\n");
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		writer.close();
	}
}
