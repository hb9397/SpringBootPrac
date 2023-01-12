package com.kakao.springbootboardprac.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


// JPA 동작을 감시 - 실행 클래스의 @EnableJpaAuditing 과 짝
@EntityListeners(value= AuditingEntityListener.class)
@Getter
@MappedSuperclass // 테이블 생성을 하지 않는 Entity
public class BaseEntity {
    @CreatedDate
    @Column(name="regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name="moddate")
    private LocalDateTime modDate;
}

