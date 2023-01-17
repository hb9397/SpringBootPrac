package com.kakao.springbootboardprac;

import com.kakao.springbootboardprac.domain.Board;
import com.kakao.springbootboardprac.domain.Member;
import com.kakao.springbootboardprac.domain.Reply;
import com.kakao.springbootboardprac.persistence.BoardRepository;
import com.kakao.springbootboardprac.persistence.MemberRepostitory;
import com.kakao.springbootboardprac.persistence.ReplyRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest

public class RepositoryTest {
    // 필드 주입, 테스트에서는 굳이 인스턴스를 사용할 필요가 없기 때문에 TEST용도로 사용한다.
    // RepositoryTest는 Service에서 사용할 메서드 종류를 Test하는 것
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepostitory memberRepostitory;

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    // 회원 데이터 삽입
    public void insertMember(){
        for(int i = 0; i <= 100; i++){
            // Member에는 Setter가 없기 때문에 build로 생성
            Member member = Member.builder()
                    .email("user" + i + "@kakao.com")
                    .password("1111")
                    .name("USER"+i)
                    .build();
            memberRepostitory.save(member);
        }
    }

    @Test
    // 게시판 데이터 삽입
    public void insertBoard(){
        for(int i=0; i<= 100; i++){
            Member member = Member.builder()
                    .email("user" + i + "@kakao.com")
                    .build();

            Board board = Board.builder()
                    .title("제목..." + i)
                    .content("내용..." + i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        }
    }

    @Test
    // 댓글 삽입 TEST
    public void insertReply(){
        for(int i=1; i<= 300; i++){
            long bno = (long)(Math.random() * 100) + 2;
            Board board = Board.builder().bno(bno).build();
            Reply reply = Reply.builder()
                    .text("댓글..." + i)
                    .replyer("guest" + i)
                    .board(board)
                    .build();
            replyRepository.save(reply);
        }
    }

    @Test
    @Transactional // 두개의 쿼리가 처리 될 때까지 JPA의 connection을 끊지 않는다.
    // 게시글 하나를 가져오는 메서드
    public void readBoard(){
        Optional <Board> result = boardRepository.findById((100L));
        // 첫번째 쿼리
        Board board = result.get();
        System.out.println(board);
        // 두번째 쿼리
        System.out.println(board.getWriter());
    }

    @Test
    @Transactional
    // 댓글 하나를 가져오는 메서드
    public void readReply(){
        Optional <Reply> result = replyRepository.findById((34L));
        Reply reply = result.get();
        System.out.println(reply);
        System.out.println(reply.getBoard());
    }

    @Test
    public void joinTest1(){
        Object result = boardRepository.getBoardWithWriter(100L);

        // 결과가 배열
        System.out.println(result);

        Object [] ar = (Object[]) result;
        System.out.println(Arrays.toString(ar));

        // 위에서 Object 배열을 출력하면 Board와 Member 를 쪼갤 수도 있게 되어있다.
        Board board = (Board) ar[0];
        Member member= (Member) ar[1];

        System.out.println(board);
        System.out.println(member);
    }

    @Test
    public void testJoin2(){
        List<Object []> result = boardRepository.getBoardWithReply(100L);

        for(Object [] ar : result){
            System.out.println(Arrays.toString(ar));
        }
    }

    @Test
    void testJoin3(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page <Object []> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {
            Object [] ar = (Object []) row;
            System.out.println(Arrays.toString(ar));
        });
    }

    @Test
    public void testSearchTest1(){
        boardRepository.search1();
    }

    @Test
    public void testSearchPage() {
        Pageable pageable = PageRequest.of(0,10,
                Sort.by("bno").descending()
                        .and(Sort.by("title").ascending()));

        // 제목에 1이 포함된 데이터 검색
        Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);

        // Object 배열의 결과는 순회로 값을 확인
        // System.out.println(result.getContent());
        for(Object [] row : result.getContent()){
            System.out.println(Arrays.toString(row));
        }
    }


    @Test
    // 게시글 가지고 댓글 가져오는 메서드
    public void testListReply(){
        List<Reply> replyList = replyRepository.findByBoardOrderByRno(Board.builder()
                        .bno(27L).build());

        replyList.forEach(reply -> System.out.println(reply));
    }
}
