    package com.kakao.springbootboardprac.persistence;

    import com.kakao.springbootboardprac.domain.Board;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;

    import java.util.List;

    /*
    Spring Data JPA를 사용하여 persistence Layer를 구성할 때 @Repository 어노테이션으로 빈 등록하지 않는 이유는?
    spring-data-jpa의 JpaRepository를 상속받아 사용 시 의존성 주입 및 빈등록을 위해 @Repository 어노테이션을 사용할 필요가 없다.
    Spring Data JPA랑 JPA랑은 다른 걸 또 알아야 하드라
    Spring에서 JPA를 편하게 쓰기 위한게 Spring Data JPA이고 그냥 JPA를 사용하려면 우리 인터페이스 쓰는것보다 구체적으로 작성하드라
    */
    // Proxy Pattern 을 이용해서 SearchBoardRepository 인터페이스를 상속받아 해당 인터페이스 구현체의 메서드를 사용할 수 있다.
    public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository { //
        // Board 데이터를 가져올 때 Writer 정보까지 한번에 가져오는 메서드 - Board 에 지연로딩을 사용했기 때문에 필요
        @Query("select b, w from Board b left join b.writer w where b.bno=:bno")
        public Object getBoardWithWriter(@Param("bno") Long bno);

        // 글 번호를 받아서 Board와 그리고 Board와 관련된 Reply 정보 찾아오기
        // Board 1개에 여러개의 Reply가 존재
        // Board와 Reply를 결합한 형태의 목록으로 반환한다.
        @Query("select b, r from Board b left join Reply r on r.board = b where b.bno=:bno")
        // 쿼리의 반환값은 Reply에 board 를 leftjoin 한 Board Entity의 instance, Reply Entity의 instance이다.
        List<Object []> getBoardWithReply(@Param("bno") Long bno);

        // 게시글 목록과 작성자 정보 그리고 댓글의 개수를 가져오는 메서드 - 게시글 목록 데이터 가져오기
        // 개수를 구하기 위해서는 하나의 그룹으로 묶어 주어야 한다.
        // 또한 반환 값은 3개의 테이블을 조인 하는 것이므로 3개의 배열을 반환한다.
        // Board, Member, Reply 전체 데이터를 join 해서 사용하므로 Page를 사용한다.
        @Query("select b, w, count(r) from Board b left join b.writer w left join Reply r on r.board = b group by b")
        Page<Object []> getBoardWithReplyCount(Pageable pageable);

        // 글번호를 가지고 데이터를 찾아오는 메서드 구현 - 상세보기
        @Query("select b, w, count(r) from Board b left join b.writer w left outer join Reply r on r.board = b where b.bno = :bno")
         Object getBoardByBno(@Param("bno") Long bno);
    }
