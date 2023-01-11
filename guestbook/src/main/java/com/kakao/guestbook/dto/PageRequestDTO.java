package com.kakao.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
@Data
@Builder
public class PageRequestDTO {
    // 현재 페이 번호
    private int page;
    private int size;

    public PageRequestDTO(){
        // 기본값 설정
        this.page= 1;
        this.size = 10;

    }

    // Repository 에게 전달할 Pageable 객체를 만들어주느 메서드
    public Pageable getPageable(Sort sort){
        return PageRequest.of(page-1, size, sort);
    }

}
