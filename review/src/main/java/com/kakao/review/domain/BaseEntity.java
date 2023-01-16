package com.kakao.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
// AuditingEntityListener을 사용하기 위해선 어플리케이션 구동 클래스에  @EnableJpaAuditing을 달아줘야 한다
// AuditingEntityListener 는 어노테이션으로 @CreatedBy(작성자) , @CreatedDate(작성일) @LastModifiedDate(수정일) @LastModifiedBy(수정자) 를
// 자동으로 사용할 수 있게 해준다.
@EntityListeners(value= AuditingEntityListener.class)
// 다른 Entity에서 사용하는 공통된 속성을 갖는 Entity로 따로 테이블을 만들지 않는다.
// 해당 Entity의 속성을 가질 Entity들이 해당 Entity를 상속 받는다.
@MappedSuperclass
abstract class BaseEntity {
    @CreatedDate
    @Column(name="regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name="moddate")
    private LocalDateTime modDate;
}
