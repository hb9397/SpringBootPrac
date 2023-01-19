package com.kakao.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@RequiredArgsConstructor // 생성자 주입
@RequestMapping("/member") // 공통 URL
@Controller
public class MemberController {
    @GetMapping("/login")
    public void login(String error, String logout){
        // login 을 실패한 경우 파라미터로 error가 생긴다.
        // logout 한 뒤 logout으로 이동 했을 때의 파라미터 이다.
        if(error != null){

        }
        if(logout != null){

        }
    }
}
