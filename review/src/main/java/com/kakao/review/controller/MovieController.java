package com.kakao.review.controller;

import com.kakao.review.dto.MovieDTO;
import com.kakao.review.dto.PageRequestDTO;
import com.kakao.review.dto.PageResponseDTO;
import com.kakao.review.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@RequestMapping("/movie") // 공통 URL
@RequiredArgsConstructor // 생성자 주입 사용을 위해서 사용 -> final 키워드
@Controller
public class MovieController {
    private final MovieService movieService;

    // 등록을 위한 메서드 처리
    @GetMapping("/register")
    public void register(){}

    @PostMapping("/register")
    public String register(MovieDTO movieDTO, RedirectAttributes redirectAttributes){
        log.info("movieDTO: "+movieDTO);

        Long mno = movieService.register(movieDTO);
        redirectAttributes.addFlashAttribute("Msg", mno+" 삽입 성공");

        return "redirect:/movie/list";
    }

    // 영화 목록 보기를 위한 메서드 처리
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        PageResponseDTO pageResponseDTO =
                movieService.getList(pageRequestDTO);

        model.addAttribute("result", pageResponseDTO);
    }

    // read로 왔을 대 상세보기를 출력하도록 설정
    @GetMapping("/read")
    public void read(Long mno,
                     @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
                     Model model){
        MovieDTO movieDTO = movieService.getMovie(mno);
        model.addAttribute("dto", movieDTO);
    }
}
