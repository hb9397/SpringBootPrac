package com.kakao.review.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
// 외래키 값은 지연로딩으로 받아오므로 ToString에서 외래키 값 제외
@ToString(exclude = {"movie", "member"})
@Entity
@Table(name="movie_review")
public class Review{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewnum;

    private int grade;
    private String text;


    // Member 도 여러개의 Review 를 가질수 있고, Movie도 여러개의 Review를 가질 수 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
