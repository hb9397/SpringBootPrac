package com.kakao.springbootboardprac.service;

import com.kakao.springbootboardprac.domain.Board;
import com.kakao.springbootboardprac.domain.Reply;
import com.kakao.springbootboardprac.dto.ReplyDTO;

import java.util.List;

public interface ReplyService {
    // ReplyDTO를 Reply Entity로 변환하는 메서드
    default Reply dtoToEntity(ReplyDTO dto){
        Board board = Board.builder()
                .bno(dto.getBno())
                .build();

        Reply reply = Reply.builder()
                .replyer(dto.getReplyer())
                .text(dto.getText())
                .board(board)
                .build();

        return reply;
    }

    // Reply Entity 를 ReplyDTO 로 전환하느 메서드
    default ReplyDTO entityToDTO(Reply reply){
        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
        return dto;
    }

    // 댓글 등록
    Long register(ReplyDTO replyDTO);

    // 댓글 목록 가져오기
    List<ReplyDTO> getList(Long bno);

    // 댓글 수정
    Long modify(ReplyDTO replyDTO);

    // 댓글 삭제
    Long remove(Long rno);
}
