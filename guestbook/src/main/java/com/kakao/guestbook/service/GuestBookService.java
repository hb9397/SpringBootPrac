package com.kakao.guestbook.service;

import com.kakao.guestbook.domain.GuestBook;
import com.kakao.guestbook.dto.GuestBookDTO;
import com.kakao.guestbook.dto.PageRequestDTO;
import com.kakao.guestbook.dto.PageResponseDTO;
import org.springframework.data.domain.PageRequest;

public interface GuestBookService {
    // DTO 를 Entity로 변환해주는 메서드
    default GuestBook dtoToEntity(GuestBookDTO dto){

        // 삽입 날자와 수정 날짜는 entity가 삽입되거나 수정될 때 생성되므로 옮겨줄 필요가 없다
        GuestBook entity = GuestBook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();

        return entity;
    }

    // entity 를 dto로 변환해주는 메서드
    // 생성/수정 날짜를 포함해 전부 옮겨야 한다.
    default GuestBookDTO entityToDto(GuestBook entity){
        GuestBookDTO dto = GuestBookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return dto;
    }

    // 데이터 삽입을 위한 메서드
    // 대부분의 경우 삽입은 DTO를 매개변수로 받고
    // 반환 형은 삽입된 데이터를 그대로 받거나 성공과 실패에 대한 Boolean, 영향을 받은 행의 개수를 의미하는 int, 기본키의 값을 반환하게 지정한다.
    public Long register(GuestBookDTO dto);

    // 목록보기 를 위한 메서드
    PageResponseDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO);
}

