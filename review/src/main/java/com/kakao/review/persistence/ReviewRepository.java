package com.kakao.review.persistence;

import com.kakao.review.domain.Member;
import com.kakao.review.domain.Movie;
import com.kakao.review.domain.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 테이블 전체 데이터를 가져오므로 Paging 이 가능하다.
    // 기본키를 가지고 데이터 1개 가져오기
    // 데이터 삽입과 수정(기본키를 조건으로 하는)에 사용되는 메서드 제공
    // 기본키를 가지고 삭제하는 메서드와 Entity를 가지고 삭제

    // JPA Repository에는 아래의 방법들로 쿼리를 작성할 수 있음음
    // 이름을 기반으로 하는 메서드 생성 가능 -> 테이블 하나를 사용할 때 사용하는 것을 권장
    @EntityGraph(attributePaths = {"member"},type= EntityGraph.EntityGraphType.FETCH)
    // 해당 메서드 이용시에는지연로딩으로 트랜잭션 설정을 하지 않으면 불러올 수 없는
    // Member의 자료형의 member 외래키 까지 한 번에 불러오도록 설정
    List<Review> findByMovie(Movie movie);

    // 회원탈퇴시 회원 정보를 가지고 리뷰에 대해 실제로 리뷰를 삭제하는 메서드
    void deleteByMember(Member member);

    // JPQL을 이용한 쿼리 작성 가능 -> 테이블 두개 이상일 때(JOIN) 사용하는 것을 권장
    @Modifying // JPQL 수정 가능하게 해주는 어노테이션
    // 회원 탈퇴시 리뷰를 삭제하지 않고 리뷰를 작성한 회원의 정보를 null로 만드는 방식의 메서드
    @Query("update Review r set r.member.mid = null where r.member.mid = :mid")
    void updateByMember(@Param("mid") Long mid);

    // QueryDSL 을 이용한 쿼리 작성 가능 -> 동적 쿼리를 사용해야 할 때 사용하는 것을 권장
}
