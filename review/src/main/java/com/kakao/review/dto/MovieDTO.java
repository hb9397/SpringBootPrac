package com.kakao.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDTO {
    private Long mno;
    private String title;

    // review의 grade 평균
    private double avg;

    // 리뷰 개수
    private Long reviewCnt;

    // 등록일 과 수정일
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    // builder() 라는 메서드를 이용해서 생성할 때 기본값으로 사용할 수 있게 처리하는 어노테이션
    @Builder.Default
    // 영화 정보를 반환 할 때 영화 이미지도 반환해야 한다.
    // MovieImageDTO 는 null 일 수 있기 때문에 Vector(List, Array)
    private List<MovieImageDTO> imageDTOList = new ArrayList<>();
}
