package com.kakao.review.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewDTO {
    private Long reviewNum;
    private Long mid;
    private Long mno;
    private String nickname;
    private String email;
    private int grade;
    private String text;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
