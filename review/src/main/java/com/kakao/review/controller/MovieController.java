package com.kakao.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@RequestMapping("/movie") // 공통 URL
@RequiredArgsConstructor // 생성자 주입 사용을 위해서 사용 -> final 키워드
@Controller
public class MovieController {
    @GetMapping("/register")
    public void register(){}
}
