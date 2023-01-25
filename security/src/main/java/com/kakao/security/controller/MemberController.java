package com.kakao.security.controller;

import com.kakao.security.dto.ClubMemberJoinDTO;
import com.kakao.security.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@RequiredArgsConstructor // 생성자 주입
@RequestMapping("/member") // 공통 URL
@Controller
public class MemberController {
    @GetMapping("/login")
    public void login(String error, String logout) {
        // error는 로그인이 실패했을 경우의 파라미터
        log.info("error: " + error);

        // logout은 로그아웃한 후 로그인으로 이동했을 때 파라미터
        log.info("logout: " + logout);

        if (error != null) {

        }
        if (logout != null) {
            log.info("[MemberContoller] logout !");

        }

    }

    // 회원 가입 GET, POST 요청
    private MemberService memberService;
    // 회원가입 페이지로 이동
    @GetMapping("/join")
    public void join(){ // 일반적으로 회원가입으로 이동할 때는 매개변수, 반환값 이 없다.
        log.info("회원가입으로 이동");
    }

    @PostMapping("/join")
    public String join(ClubMemberJoinDTO memberJoinDTO, RedirectAttributes rattar){
        log.info(memberJoinDTO);
        try {
            // 성공
            memberService.join(memberJoinDTO);
        }catch (Exception e){
            // 실패
            rattar.addFlashAttribute("error", "mid");
            return "redirect:/member/join";
        }
        // 성공
        rattar.addFlashAttribute("result", "success");
        return "redirect:/member/login";
    }
}
