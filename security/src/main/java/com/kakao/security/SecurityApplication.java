package com.kakao.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
// @EntityListeners(value= AuditingEntityListener.class)를 갖는 Entity 클래스에서
// AuditingEntityListener을 사용하기 위해선 어플리케이션 구동 클래스에  @EnableJpaAuditing을 달아줘야 한다
// AuditingEntityListener 는 어노테이션으로 @CreatedBy(작성자) , @CreatedDate(작성일) @LastModifiedDate(수정일) @LastModifiedBy(수정자) 를
// 자동으로 사용할 수 있게 해준다.
@EnableJpaAuditing // JPA 감지

public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

}
