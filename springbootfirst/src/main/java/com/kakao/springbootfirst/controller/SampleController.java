package com.kakao.springbootfirst.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 기본 패키지 내부에 있어야 한다.
// View 대신에 문자열(CSV) 난 JSON을 리턴하게 만들고자 할 때 사용하는 어노테이션
@RestController
public class SampleController {
    // hello 라는 요청을 GET 방식으로 요청한 경우에 설정
    @GetMapping("/hello")
    // String 을 반환하면 일반 문자열로 출력
    // VO나 List를 반환하면 JSON 문자열로 출력한다.
    public String [] hello(){
        return new String [] {"STS", "InteliJ"};
    }
}
