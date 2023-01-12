package com.kakao.springbootboardprac.controller;

import com.kakao.springbootboardprac.board.PageRequestDTO;
import com.kakao.springbootboardprac.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@RequiredArgsConstructor
@Controller
public class BoardController {
    // Board service를 주입 받아서 사용
    private final BoardService boardService;

    // 기본 요청 생
    @GetMapping({"/", "/board/list"})
    public String list(PageRequestDTO pageRequestDTO, Model model){
        log.info("기본 목록 보기 요청");
        model.addAttribute("result", boardService.getList(pageRequestDTO));

        return "board/list";
    }
}
