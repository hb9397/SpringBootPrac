package com.kakao.review.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
// ToString을 만들 때 외래키를 참조하는 관계설정 시 지연로딩을 사용했기 때문에 movie의 값은 쿼리로 접근하지 않는 이상 null이 되므로
// toString 사용시 NullPointerException이 발생하기 때문에 제외 시킨다.
@ToString(exclude = "movie")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="movie_image")
// 부모 테이블을을 만들때 이 속성의 값을 포함 시켜서 생성하게 설정하는 Annotation(생략 가능)_주로 1:1 관계에서 많이 사용한다.
// 현재는 제거하고 사용해도 큰 의미가 없다.
@Embeddable
public class MovieImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Identity로 설정하면 DB에 기본키 생성 전략을 위임한다. 보통은 auto_increment
    private Long inum;

    private String uuid; // 파일 이름이 겹치지 않도록 하기 위해서 추가

    private String imgName; // 파일 이름

    // 하나의 디렉토리에 너무 많은 파일이 저장되지 않도록 업로드한 날짜 별로 파일을 기록하기 위한 디렉토리 이름
    private String path;

    // 하나의 Movie 가 여러개의 MovieImage를 소유
    // 외래키로 영화의 정보를 갖는 Movie Entity의 기본키를 외래키로 갖는다.
    @ManyToOne(fetch= FetchType.LAZY) // 지연로딩 -> 외래키 참조 검색할 때 사용하지 않으면 Movie의 Entity 정보는 가져오지 않는다.
    private Movie movie; // movie_mno로 DB에 등록 된다.
}
