package com.kakao.guestbook.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GuestBook extends BaseEntity{
    // 기본키 지정
    @Id
    // 기본키 자동으로 생성
    // 직접 만들거라면 필요 없다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gno;

    // 컬럼 지정 - 제목 100자, not null
    @Column(length = 100, nullable = false)
    private String title;

    // 컬럼 지정 - 내용 1500자, not null
    @Column(length = 1500, nullable = false)
    private String content;

    // 컬럼 지정 - 작성자 50자, not null
    @Column(length = 50, nullable = false)
    private String writer;
}
