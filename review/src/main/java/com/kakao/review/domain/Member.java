package com.kakao.review.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name="movie_member")
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;
    private String email;
    private String pw;
    private String nickname;
}
