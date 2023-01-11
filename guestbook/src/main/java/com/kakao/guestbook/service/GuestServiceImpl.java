package com.kakao.guestbook.service;

import com.kakao.guestbook.domain.GuestBook;
import com.kakao.guestbook.dto.GuestBookDTO;
import com.kakao.guestbook.dto.PageRequestDTO;
import com.kakao.guestbook.dto.PageResponseDTO;
import com.kakao.guestbook.persistence.GuestBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Log4j2
@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestBookService{
    // Service는 Respository 를 주입받아서 사용한다.
    private final GuestBookRepository guestBookRepository;

    public Long register(GuestBookDTO dto){
        // 파라미터가 제대로 넘어오는지 확인
        log.info("삽입 데이터" + dto.toString()); // 항상 확인하는 습관

        // repository의 메서드 매개변수로 변형
        GuestBook entity = dtoToEntity(dto);

        // save를 할 때 설정한 내역을 가지고 필요한 데이터를 설정하면 gno, regDate, modDate가 설정된다.
        guestBookRepository.save(entity);

        return entity.getGno();
    }

    @Override
   public PageResponseDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO){
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        // 데이터 찾아오기
        Page<GuestBook> result = guestBookRepository.findAll(pageable);

        // Function 인터페이스 생성
        // 데이터 목록을 받아 데이터 목록을 순회하면서, 제공된 메서드가 리턴 하는 목록을 변경해주는 람다
        Function <GuestBook, GuestBookDTO> fn = (entity -> entityToDto(entity));

        return new PageResponseDTO<>(result, fn);
    }
}
