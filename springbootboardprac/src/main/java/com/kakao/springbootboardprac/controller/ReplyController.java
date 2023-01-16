package com.kakao.springbootboardprac.controller;

import com.kakao.springbootboardprac.dto.ReplyDTO;
import com.kakao.springbootboardprac.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController // RestAPI
@RequestMapping("/replies") // 공통 URL 설정
public class ReplyController {
    // 서비스 생성자 주입
    private final ReplyService replyService;

    // 게시글 번호를 가지고 댓글을 리턴해주는 메서드
    @GetMapping(value = "/board/{bno}")
    // 반환형 ResponseEntity는 List로 이뤄져 있고 List는 ReplyDTO로 이뤄져있다.
    public ResponseEntity<List<ReplyDTO>> getByBoard(@PathVariable("bno") Long bno){
        log.info("bno: "+bno);

        replyService.getList(bno);

        return new ResponseEntity<>(replyService.getList(bno), HttpStatus.OK);
    }

    // 댓글 추가 요청
    @PostMapping("")
    public ResponseEntity<Long> register(
            @RequestBody ReplyDTO replyDTO){
        log.info(replyDTO);

        Long rno = replyService.register(replyDTO);
        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    // 댓글 삭제 요청
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno){
        log.info("Rno: " + rno);

        replyService.remove(rno);
        return new ResponseEntity<>(rno + "삭제", HttpStatus.OK);
    }

    // 댓글 수정 요청
    @PutMapping("/{rno}")
    public ResponseEntity<Long> modify (@RequestBody ReplyDTO replyDTO){

        log.info(replyDTO);

        Long rno = replyService.modify(replyDTO);
        return new ResponseEntity<>(rno, HttpStatus.OK);
    }
}
