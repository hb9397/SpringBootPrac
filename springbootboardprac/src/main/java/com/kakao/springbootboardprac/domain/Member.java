package com.kakao.springbootboardprac.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity // Entity 명시

@Table(name="tbl_member") // table 이름 설정

// Setter 는 생성하지 않는다.
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member extends  BaseEntity{ // 공통으로 사용할 컬럼을 속성으로 가진 Entity 상속

    @Id
    private String email;

    private String password;

    private String name;

}
