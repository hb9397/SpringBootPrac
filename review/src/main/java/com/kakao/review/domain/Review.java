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
public class Review extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewnum;

    private int grade;
    private String text;

    // grade 와 text를 수정 가능하도록 하는 메서드
    public void changeGrade(int grade){
        this.grade = grade;
    }

    public void changeText(String text){
        this.text = text;
    }


    // Member 도 여러개의 Review 를 가질수 있고, Movie도 여러개의 Review를 가질 수 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
