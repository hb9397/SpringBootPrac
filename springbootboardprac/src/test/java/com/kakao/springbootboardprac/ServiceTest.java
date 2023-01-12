package com.kakao.springbootboardprac;

import com.kakao.springbootboardprac.board.BoardDTO;
import com.kakao.springbootboardprac.board.PageRequestDTO;
import com.kakao.springbootboardprac.board.PageResponseDTO;
import com.kakao.springbootboardprac.domain.Board;
import com.kakao.springbootboardprac.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class ServiceTest {
    // ServiceTest 는 Contorller에서 사용할 메서드의 종류를 테스트 하는 것
    @Autowired
    private BoardService boardService;

    @Test
    public void registerTest(){
        BoardDTO dto = BoardDTO.builder()
                .title("등록 테스트")
                .content("등록을 테스트 합니다.")
                .writerEmail("user1@kakao.com")
                .build();
        Long bno = boardService.register(dto);
        System.out.println(bno);
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResponseDTO<BoardDTO, Object []> result = boardService.getList(pageRequestDTO);

        System.out.println(result);
    }

    @Test
    public void testGet(){
        Long bno = 100L;
        BoardDTO boardDTO = boardService.get(bno);
        System.out.println(boardDTO);
    }

    @Test
    public void testDelete(){
        boardService.removeWithReplies(100L);
    }

    @Test
    public void testUpdate() {
        BoardDTO dto = BoardDTO.builder()
                .bno(99L)
                .title("제목 변경")
                .content("내용 변경이다 ,.,,.")
                .build();
        System.out.println(boardService.modify(dto));
    }
}
