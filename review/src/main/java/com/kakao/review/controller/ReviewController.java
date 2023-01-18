package com.kakao.review.controller;

import com.kakao.review.dto.ReviewDTO;
import com.kakao.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2

// 서비스 생성자 주입
@RequiredArgsConstructor

// REST API Controller
@RestController

// 공통된 URL
@RequestMapping("/reviews")
public class ReviewController {
    // 서비스 생성자 주입
    private final ReviewService reviewService;

    // 영화 번호에 해당하는 댓글 목록 처리
    @GetMapping("/{mno}/list")
    public ResponseEntity<List<ReviewDTO>> list(@PathVariable("mno") Long mno){
        List<ReviewDTO> result = reviewService.getList(mno);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 댓글을 추가하는 요청에 대한 처리
    @PostMapping("/{mno}")
    // Post 요청의 Body 에 있는 요청 변수는 @RequestBody 에 의해 이름이 같다면 알아서 매핑되어 받는다
    public ResponseEntity<Long> addReview(@PathVariable("mno") Long mno,
                                      @RequestBody ReviewDTO reviewDTO){
        Long result = reviewService.register(reviewDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 댓글을 수정하는 요청에 대한 처리
    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> updateReview(
            @PathVariable("mno") Long mno,
            @PathVariable("reviewnum") Long reviewnum,
            @RequestBody ReviewDTO reviewDTO
    ){
        Long result = reviewService.modify(reviewDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 댓글을 삭제하는 메서드
    @DeleteMapping("/{mno}")
    // Post 요청의 Body 에 있는 요청 변수는 @RequestBody 에 의해 이름이 같다면 알아서 매핑되어 받는다
    public ResponseEntity<Long> addReview(@PathVariable("mno") Long mno,
                                          @PathVariable Long reviewnum){
        Long result = reviewService.remove(reviewnum);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
