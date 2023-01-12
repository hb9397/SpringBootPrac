package com.kakao.springbootboardprac.service;

import com.kakao.springbootboardprac.board.BoardDTO;
import com.kakao.springbootboardprac.board.PageRequestDTO;
import com.kakao.springbootboardprac.board.PageResponseDTO;
import com.kakao.springbootboardprac.domain.Board;
import com.kakao.springbootboardprac.domain.Member;
import jakarta.transaction.Transactional;

public interface BoardService {
    // DTO를 Entity로 변환해주는 메서드
    default Board dtoToEntity(BoardDTO dto){
        Member member = Member.builder()
                .email(dto.getWriterEmail()).build();


        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member) // Writer 를 이메일로 바로 못쓰나?
                .build();
        return board;
    }

    // Entity를 DTO로 변환해주는 메서드
    default BoardDTO entityToDTO(Board board, Member member, Long replyCount){
        BoardDTO dto = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())
                .build();
        return dto;
    }

    // 게시글 등록 메서드
    Long register(BoardDTO dto);

    // 게시글 목록 보기 메서드
    PageResponseDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    // 게시글 상세 보기 메서드
     BoardDTO get(Long bno);

     // 게시글 번호를 가지고 댓글을 삭제하는 메서드
    // 게시글이 삭제되면 댓글도 삭제되야 하니까
    // 삭제를 두 번(게시글, 댓글) 수행하기 때문에 트랜잭션 적용
    @Transactional
    void removeWithReplies(Long bno);


    // 게시글 수정 메서드
    Long modify(BoardDTO dto);
}
