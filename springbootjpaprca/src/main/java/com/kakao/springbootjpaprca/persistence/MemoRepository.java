package com.kakao.springbootjpaprca.persistence;
import com.kakao.springbootjpaprca.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MemoRepository extends JpaRepository<Memo, Long>{
}
