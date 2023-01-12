package com.kakao.springbootboardprac.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
@Getter
public class Reply extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rno;

    private String text;
    private String replyer;

    @ManyToOne(fetch = FetchType.LAZY)
    // Board 전체를 들고 와서 Board 의 기본키인 board_bno 로 설정된다.
    private Board board;
}
