package com.kakao.springbootboardprac.board;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDTO<DTO, EN> { // DTO, Entity
    // 데이터 목록을 저장할 리스트
    private List<DTO> dtoList;

    // 페이지 번호 관련 속성

    private int totalPage; // 전체 페이지 개수
    private int page; // 현재 페이지 번호
    private int size; // 페이지 당 데이터 출력 개수
    private int start, end; // 페이지 시작 번호와 끝 번호
    private boolean prev, next; // 이전과 다음페이지에 대한 여부
    private List<Integer> pageList;// 페이지 번호 목록

    // 검색결과 (Page<Board>) 를 가지고 데이터를 생성해주는 메서드
    // Entity가 반환되고 Function 인터페이스가 이 매개변수
    // PageResponseDTO 생성자
    public PageResponseDTO(Page<EN> result, Function<EN, DTO>fn){
        // 검색 결과 목륵을 DTO의 List로 변환해주는 메서드
        dtoList = result.stream().map(fn).collect(Collectors.toList());

        // 전체 페이지 개수 구하기
        totalPage = result.getTotalPages();
        // 페이지 번호 목록 관련 속성을 결정하는 메서드 호출
        makePageList(result.getPageable());
    }

    // 페이지 번호 목록 관련 속성을 결정하는 메서드
    private void makePageList(Pageable pageable){
        // 현재 페이지 번호
        this.page = pageable.getPageNumber() + 1;

        // 데이터 개수
        this.size = pageable.getPageSize();

        // 임시로 마지막 페이지 번호 계산
        int tempEnd = (int)(Math.ceil(page/10.0)) * 10;

        // 시작하는 페이지 번호
        start = tempEnd -  9;

        // 이전 페이지 목록 여부
        prev = start > 1;

        // 게시글 데이터 전체를 페이징 했을 때 진짜 끝페이지
        end = totalPage > tempEnd ? tempEnd : totalPage;

        // 다음 페이지 목록 여부
        next = totalPage > end;

        // 페이지 번호 목록 여부
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
