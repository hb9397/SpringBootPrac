package com.kakao.guestbook.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// 테이블을 생성하지 않는 Entity

@MappedSuperclass

// Entity qusghks 감시
@EntityListeners(value = {AuditingEntityListener.class})

@Getter
public class BaseEntity {
    // 생성날짜
    @CreatedDate
    @Column(name="regdate", updatable = false)
    private LocalDateTime regDate;

    // 수정된 날짜1
    @LastModifiedDate
    @Column(name ="moddate")
    private LocalDateTime modDate;
}
