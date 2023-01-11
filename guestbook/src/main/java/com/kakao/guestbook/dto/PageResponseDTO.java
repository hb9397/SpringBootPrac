package com.kakao.guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// 다른 종류의 Entity 에서도 사용할 수 있도록 하기 위해서 Generic 사용
@Data
public class PageResponseDTO <DTO, EN>{
    // 데이터 목록
    private List<DTO> dtoList;

    // 전체 페이지 개수
    private int totalPage;

    // 현재 페이지 번호
    private int page;

    // 하나의 페이지에 출력할 데이터 개수
    private int size;

    // 페이지 번호에서 시작하는 페이지 번호, 끝나는 페이지 번호
    private int start, end;

    // 이전과 다음 페이지 여부
    private boolean prev, next;

    // 페이지 번호 목록
    private List<Integer> pageList;

    // Paging 결과를 가지고 추가한 항목들을 계산해주는 메서드
    private void makePageList(Pageable pageable){
        // 현재 페이지 번호, JPA 는 페이지 번호가 0부터 시작한다.
        // 전체 클래스의 page 변수를 사용하는것
        this.page = pageable.getPageNumber() + 1;

        // 페이지 당 데이터 개수
        // 전체 클래스의 size 변수를 사용하는것
        this.size = pageable.getPageSize();

        // 임시로 마지막 페이 번호 생성
        // 페이지 번호를 10개 단위로 출력
        // 1 부터 10까지는 마지막 페이지 목록이 10, 11 부터 20까지는 20, ...
        // 10으로 나눠서 소수가 있으면 올림한 뒤 곱하기 10
        int tempEnd = (int) (Math.ceil(page/10.0)) * 10;

        // 시작하는 번호
        start = tempEnd - 9;

        // 이전 페이지 존재 여부
        prev = start > 1;

        // 마지막 페이지 번호 계산]
        // 전페 페이지가 임시 마지막 페이 숫자보다 크면 임시 마지막 페이지를, 임시 마지막 페이지가 전체 마지막 페이지 보다 작으면 totalPage가
        // 마지막 목록
        end = totalPage > tempEnd ? tempEnd : totalPage;

        // 다음 페이지 여부
        // 전체 페이지가 임시 마지막 페이지 보다 크면 다음 페이지 가 있는 것
        next = totalPage > tempEnd;

        // 페이지 번호 목록
        // start 부터 end 까지를 스트림으로 만들ㅇ서 List 로 변환
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

    // Page를 함수로 적용해서 List로 변환해주는 메서드
    // 첫번째 매개변수가 Page 단위의 Entity이고 두번째 는 데이터 변환을 위한 메서드
    // 첫번째 매개변수는 반환값을 만들기 위해 사용할 변수, 두번째 매개변수는 반환값
    public PageResponseDTO(Page<EN> result, Function<EN, DTO> fn){
        // EN(Entity) DTO(클래스 타입) 을 변환해주는 함수를 매개변수로 받아서 DTO 타입의 List로 변환해준다.
        // Java 에서 는 함수를 매개변수로 받을수 없어서 Function 인터 페이스를 매개변수로 사용
        dtoList = result.stream().map(fn).collect(Collectors.toList());

        // 전체 페이지 개수
        totalPage = result.getTotalPages();

        // 페이지 목록 메서드 호출
        makePageList(result.getPageable());
    }
}


