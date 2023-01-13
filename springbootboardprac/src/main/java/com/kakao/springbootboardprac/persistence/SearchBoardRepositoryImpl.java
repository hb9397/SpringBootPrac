package com.kakao.springbootboardprac.persistence;

import com.kakao.springbootboardprac.domain.Board;
import com.kakao.springbootboardprac.domain.QBoard;
import com.kakao.springbootboardprac.domain.QMember;
import com.kakao.springbootboardprac.domain.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository{
    /*
    SearchBoardRepository 를 implements 받기도 하지만 QuerydslRepositorySuppor 클래스를 상속 받도록 해야한다.
    하지만 `QuerydslRepositorySupport` 는 Default Constructor 가 없기 때문에
    Constructor를 직접 생성해서 필요한 Constructor를 호출해 줘야 한다.
    또한 호출하면서 검색에 사용할 Entity 클래스를 대입해야 한다.
    */
    public SearchBoardRepositoryImpl(){
        super(Board.class);
    }

    @Override
    public Board search1() {
        // JPQL을 동적으로 생성해서 실행하기 위해서 JPQL QEntity 사용
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        // 쿼리 작성 - Board 를 기준으로
        JPQLQuery<Board> jpqlQuery = from(board);

        // member 와 조인 외래키는 board의 writer
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));

        // reply 와 조인하는데 외래키는 reply의 board
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        // 조인한 테이블에서 board의 정보는 전부 가져오고 member 에서는 email reply는 개수만 갖고 오게 하기 위해서 board를 기준으로 그룹화 시켜서 조회
        //jpqlQuery.select(board, member.email, reply.count()).groupBy((board));

        // 실제로 데이터 가져오기
        // 결과는 Board Entity에 앞서 실행한 jqpl을 fetch 로 실행시킨 결과 에서 뽑아온다
        // List<Board> result = jpqlQuery.fetch();

        // 결과를 Tuple로 받기 위해서 수정
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count());
        tuple.groupBy(board);

        List<Tuple> result = tuple.fetch();

        System.out.println(result);

        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

        // 조건 생성
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L); // bno가 0보다 큰
        booleanBuilder.and(expression);

        // 타입에 따른 조건 생성
        if (type != null) {
            // 글자 단위로 쪼개기
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            for (String t : typeArr) {
                switch (t){
                    case "t":
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }
        // 조건을 tuple 에 적용
        tuple.where(booleanBuilder);

        // 정렬 방법 설정
        tuple.orderBy(board.bno.desc());

        // 그룹화
        // 그릅화 하는 이유는 Count와 같이 조건으로 결과를 구하는 쿼리는 게시글과 댓글의 관계로 봤을 때
        // 하나의 게시글에 여러개의 댓글이 있기 때문에 이 여러개의 댓글을 하나의 게시글을 기준(기본키로)으로 그룹화 해야
        // 유의미한 결과를 낼수 있으며 비지니스 로직 상에서는 해당 게시글이 몇개의 댓글을 가질 수 있는지 알 수 있기 때문이다.
        // 만약 그룹화 하지 않으면
        /*
         위처럼 쿼리를 작성한 경우 댓글들이 참조하는 게시글번호(bno) 댓글의 개수만큼 중복되어서 결과에 담아서 가져오게 된다.
          bno=1 reply=5 일때 1번 게시글에 댓글의 개수가 5개 인데 1이라는 bno를 가진 게시글의 기본키를 5개 가져오고 댓글 개수는 1개씩 밖에 못가져옴
        */
        tuple.groupBy(board);

        // page 처리
        tuple.offset(pageable.getOffset()); // 매개변수로 받은 pageable의 시작
        tuple.limit(pageable.getPageSize()); // 매개변수로 받은 pageable의 한페이지 사이즈

        // 데이터 가져오기
        List<Tuple> result = tuple.fetch();

        // return
        return new PageImpl<Object []>(
                result.stream().map(t->t.toArray()).collect(Collectors.toList()),
                pageable, tuple.fetchCount()
        );
    }

}
