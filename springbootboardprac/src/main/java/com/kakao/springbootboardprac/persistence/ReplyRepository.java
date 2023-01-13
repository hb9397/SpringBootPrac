package com.kakao.springbootboardprac.persistence;

import com.kakao.springbootboardprac.domain.Board;
import com.kakao.springbootboardprac.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Modifying
    @Query("delete from Reply r where r.board.bno = :bno")
    void deleteByBno(@Param("bno") Long bno);

    // 게시글을 이용해서 데이터 찾아오는 메서드
    List<Reply> findByBoardOrderByRno(Board board);

}
