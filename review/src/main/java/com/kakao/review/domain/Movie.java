package com.kakao.review.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "movie")
public class Movie extends BaseEntity{
    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성 전략
    private Long mno;

    private String title;


    // 자식 테이블(Entity)의 속성들을 포함하게 하는 어노테이션으로 예시만 보이는것
    // 1:1 관계의 테이블이 아니면 크 의미가 없다.
    /*@Embedded
    private MovieImage image;*/
}
