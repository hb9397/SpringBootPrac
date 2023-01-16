package com.kakao.springbootboardprac.service;

import com.kakao.springbootboardprac.domain.Board;
import com.kakao.springbootboardprac.domain.Reply;
import com.kakao.springbootboardprac.dto.ReplyDTO;
import com.kakao.springbootboardprac.persistence.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    // 레포지토리 주입
    private final ReplyRepository replyRepository;
    @Override
    // 댓글 등록
    public Long register(ReplyDTO replyDTO) {
        Reply reply = dtoToEntity(replyDTO); // 등록할 땐 DTO -> Entity가 항상 먼저
        replyRepository.save(reply);
        return reply.getRno();
    }

    @Override
    // 댓글 목록
    public List<ReplyDTO> getList(Long bno) {
        List <Reply> result = replyRepository.findByBoardOrderByRno(Board.builder().bno(bno).build());

        // result 의 내용을 정렬하기 - 수정한 시간의 내림차순
        // ApplicationServer(Service) 에서 정렬 -> 이전까지는 Repository ( 사실상 API Server ) 단에서 수행
        result.sort(new Comparator<Reply>() {
            @Override
            public int compare(Reply o1, Reply o2) {
                return o2.getModDate().compareTo(o1.getModDate());
            }
        });

        // Reply의 List를 ReplyDTO의 List로 변경한다.
        return result.stream().map(reply -> entityToDTO(reply)).collect(Collectors.toList());
    }

    @Override
    // 댓글 수정
    public Long modify(ReplyDTO replyDTO) {
        Reply reply = dtoToEntity(replyDTO);
        replyRepository.save(reply);
        return reply.getRno();
    }

    @Override
    // 댓글 삭제
    public Long remove(Long rno) {
        replyRepository.deleteByBno(rno);
        return rno;
    }
}
