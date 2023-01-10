package com.kakao.springbootjpaprca.persistence;
import com.kakao.springbootjpaprca.domain.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
