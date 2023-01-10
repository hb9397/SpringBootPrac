package com.kakao.springbootjpaprac.persistence;
import com.kakao.springbootjpaprac.domain.Memo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long>{
    // mno 의 값이 from 부터 to 사이인 데이터들을 조회 하는 메서드
    List<Memo> findByMnoBetween(Long from, Long to);

    // mno 의 값이 from 부터 to 사이인 데이터들을 Mno의 내림 차순으로 정렬 하는 메서드
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    // 페이징을 적용해서 조회하는 메서드
    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    // 특정 Mno 보다 작은 데이터들을 삭제하는 메서드
    void deleteByMnoLessThan(Long num);

    // mno와 memoText를 매개변수로 받아서 수정하는 메서드 -> JPQL 사용
    @Transactional
    @Modifying
    // Native SQL 이 아니기 때문에 Table 이름이 아니라 Entity 클래스의 이름을 사용해야한다.
    @Query("update Memo m set m.memoText = :memoText where m.mno = :mno")
    public int updateMemoText(@Param("mno") int mno,
                              @Param("memoText")String memoText);

    @Transactional
    @Modifying
    // Native SQL 이 아니기 때문에 Table 이름이 아니라 Entity 클래스의 이름을 사용해야한다.
    // 앞의 수정 메서드와 다르게 Memo memo 인스턴스를 전체를 가져왔을 때 사용하는 경우
    @Query("update Memo m set m.memoText = :#{#param.memoText} where m.mno = :#{#param.mno}")
    public int updateMemoText(@Param("param") Memo memo);

    // JPQL 을 이용해서 페이징 처리된 Memo 인스턴스를 조회
    @Query("select m from Memo m where m.mno > :mno")
    Page<Memo> getListWithQuery(@Param("mno") Long mno,
                                Pageable pageable);

    @Query("select m.mno, m.memoText, CURRENT_DATE from Memo m where m.mno > :mno")
    Page<Object []> getObjectWithQuery(@Param("mno") Long mno, Pageable pageable);

}
