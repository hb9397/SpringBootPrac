package com.kakao.springbootboardprac.persistence;

import com.kakao.springbootboardprac.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

interface SearchBoardRepository {
    Board search1();

    // 검색을 위한 메서드
    // 검색 항목을 매개변수로 해서 페이지 단위로 데이터를 조회하는 메서드 선언
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}

