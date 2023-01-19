package com.kakao.security.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class SampleController {
    @GetMapping("/")
    public String main(){
        log.info("메인");
        return "/index";
    }

    @GetMapping("/sample/all")
    public void sampleAll(){
        log.info("모두 허용");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/sample/member")
    public void sampleMember(){
        log.info("멤버만 허용");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sample/admin")
    public void sampleAdmin(){
        log.info("관리자만 허용");
    }
}
