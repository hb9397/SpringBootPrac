package com.kakao.springbootboardprac.controller;

import com.kakao.springbootboardprac.dto.BoardDTO;
import com.kakao.springbootboardprac.dto.PageRequestDTO;
import com.kakao.springbootboardprac.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // 게시물 등록 화면 으로 이동하는 요청은 Forwarding
    // public void register(Model model){} 과 같은 형식이 가능하다.
    @GetMapping("/board/register")
    public void register(Model model){
        log.info("등록 화면으로 Forwarding");
    }

    // 게시물을 등록하는 요청을 Redirect
    // 데이터를 보낼 때 1회용 데이터를 세션에 저장하면 따로 종료하기 전까지 불필요한 데이터를 가지고 있게 된다.
    // 그래서 Spring 에서는 RedirectAttributes 라는 1회용 세션을 제공하다.
    // 계속해서 Forwarding으로 제공하면 Traffic 양이 증가 할 수 있기 때문에 늘 Redirect 와 적절히 구분해서 사용해야 된다.
    @PostMapping("/board/register")
    public String register(BoardDTO dto, RedirectAttributes rattr){
        log.info("dto : " + dto.toString());

        // 데이터 삽입
        Long bno = boardService.register(dto);
        rattr.addFlashAttribute("msg", bno + "등록");

        return "redirect:/board/list";
    }

    // 상세보기 요청 처리
    // 상세보기 후 목록으로 갈 때 해당 게시글이 있는 페이지로 돌아가게 설정
    // ModelAttribute 는 파라미터로 사용하면 넘겨 받은 데이터를 결과에 그대로 전달할 목적으로 사용한다.
    @GetMapping({"/board/read", "/board/modify"}) // 게시글 수정 링크에도 요청이 왔을 때 상세보기와 똑같이 데이터를 넘긴다.
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
            Long bno, Model model){
        log.info("글 번호: "+bno);
        BoardDTO dto = boardService.get(bno);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/board/modify")
    public String modify(BoardDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes)
    {
        log.info("dto" + dto.toString());

        //수정 처리
        boardService.modify(dto);

        // 수정후 상세보기로 이동할 때 글 번호와 현재 페이지 번호를 전달.
        redirectAttributes.addFlashAttribute("bno", dto.getBno());
        redirectAttributes.addFlashAttribute("page", requestDTO.getPage());

        log.info("수정 후 bno:" + dto.getBno());

        // 수정후 상세보기로 이동할 때 수정한 게시글의 글번호와 게시글이 있는 페이지, 검색옵션, 검색어 키워드의 정보를 가지고 가도록 처리
        return "redirect:/board/read?bno="+dto.getBno() + "&page=" + requestDTO.getPage() + "&type=" + requestDTO.getType()
                + "&keyword=" + requestDTO.getKeyword();
    }

    @PostMapping("/board/remove")
    public String remove(BoardDTO dto, RedirectAttributes redirectAttributes){
        log.info("dto: "+dto.toString());

        // 삭제
        boardService.removeWithReplies(dto.getBno()); // 게시글 삭제시 댓글도 모두 삭제되는 메서드 호출
        redirectAttributes.addFlashAttribute("msg", dto.getBno() + "삭제");

        // 삭제후 게시글 목록으로 돌아가게 처리
        return "redirect:/board/list";
    }
}
