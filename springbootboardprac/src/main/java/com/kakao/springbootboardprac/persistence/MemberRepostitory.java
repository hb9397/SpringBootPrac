package com.kakao.springbootboardprac.persistence;

import com.kakao.springbootboardprac.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepostitory extends JpaRepository<Member,String> {
}
