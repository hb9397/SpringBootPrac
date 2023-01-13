package com.kakao.springbootboardprac.service;

import com.kakao.springbootboardprac.dto.BoardDTO;
import com.kakao.springbootboardprac.dto.PageRequestDTO;
import com.kakao.springbootboardprac.dto.PageResponseDTO;
import com.kakao.springbootboardprac.domain.Board;
import com.kakao.springbootboardprac.domain.Member;
import com.kakao.springbootboardprac.persistence.BoardRepository;
import com.kakao.springbootboardprac.persistence.ReplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Log4j2
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{
    // 생성자 주입
    // Repository를 주입
    private final BoardRepository boardRepository;

    // 게시글 등록 메서드 구현
    public Long register(BoardDTO dto){
        log.info("Service: " + dto);

        Board board = dtoToEntity(dto);

        boardRepository.save(board);

        return board.getBno();
    }

    // 목록 보기 메서드 구현
    /*@Override
    public PageResponseDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO){
        log.info(pageRequestDTO);

        // Entity를 DTO로 변경하는 람다 인스턴스 생성
        Function<Object [], BoardDTO> fn = (en -> entityToDTO((Board) en[0], (Member) en[1], (Long) en[2]));

        // 목록 보기 요청 처리
        Page<Object []> result = boardRepository.getBoardWithReplyCount(
                pageRequestDTO.getPageable(Sort.by("bno").descending()));

        return new PageResponseDTO<>(result, fn);
    }*/

    // 목록 보기 메서드에 검색에 대한 설정이 적용되게 처리하도록 수정
    public PageResponseDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO){
        log.info(pageRequestDTO);

        // Entity를 DTO로 변경하는 람다 인스턴스 생성
        Function<Object [], BoardDTO> fn = (en -> entityToDTO((Board) en[0], (Member) en[1], (Long) en[2]));

        // 목록 보기 요청 처리
        Page<Object []> result = boardRepository.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending()));

        return new PageResponseDTO<>(result, fn);
    }
    // 상세보기 처리를 위한 메서드 구현
    @Override
    public BoardDTO get(Long bno){
        Object result = boardRepository.getBoardByBno(bno);
        Object [] arr = (Object[]) result;

        return entityToDTO((Board) arr[0], (Member) arr[1], (Long) arr[2]);
    }

    private final ReplyRepository replyRepository; // 리포지토리 주입
    @Override
    @Transactional // 댓글과 게시글 모두 삭제라서 삭제요청이 두번 일어나기 때무넹
    public void removeWithReplies(Long bno){

        // deleteByBno는 Query 메서드로 이름과 query를 조합해 자동으로 쿼리를 생성해주는 것 from JPA.
        replyRepository.deleteByBno(bno); // 댓글 삭제
        boardRepository.deleteById(bno);  // 게시글 삭제
    }

    @Override
    @Transactional
    public Long modify(BoardDTO dto){
        //JPA에서는 삽입과 수정 메서드가 동일한데 upsert(있으면 구조, 없으면 삽입 ) 를 하고자 하는 경우는 save를 호출하면 되지만
        // update를하고자 하면 데티터가 있는지 확인하고 수행하는 것이 종다

        Optional<Board> board = boardRepository.findById((dto.getBno()));
         // 데이터가 존재하는 경우
        if(board.isPresent()){
                board.get().changeTitle(dto.getTitle());
                board.get().changeContent(dto.getContent());
                boardRepository.save(board.get());

                return board.get().getBno();

        } else {
            return 0L;
        }
    }
}
