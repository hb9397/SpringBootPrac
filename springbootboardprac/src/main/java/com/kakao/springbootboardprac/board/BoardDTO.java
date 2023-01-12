package com.kakao.springbootboardprac.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardDTO {
    // 출력할 데이터
    private Long bno;
    private String title;
    private String content;
    private String writerName;
    private String writerEmail;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private int replyCount;
}
