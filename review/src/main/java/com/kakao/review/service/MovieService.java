package com.kakao.review.service;

import com.kakao.review.domain.Movie;
import com.kakao.review.domain.MovieImage;
import com.kakao.review.dto.MovieDTO;
import com.kakao.review.dto.MovieImageDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public interface MovieService {
    // 데이터 삽입을 위한 메서드
    Long register(MovieDTO movieDTO);

    // DTO를 Entity로 변환
    // 하나의 Entity가 아니라 Movie와 MovieImage 로 변환이 되어야 해서 Map으로 반환한다.

    default Map<String, Object> dtoToEntity(MovieDTO movieDTO){
        Map<String, Object> entityMap = new HashMap<>();

        Movie movie = Movie.builder()
                .mno(movieDTO.getMno())
                .title(movieDTO.getTitle())
                .build();

         // Map에 삽입 -> 접근은 movie로
        entityMap.put("movie", movie);

        // MovieImageDTO 의 List 가져오기
        List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();

        // MovieImageDTO 의 List를 MovieImage Entity로 변환
        if(imageDTOList != null && imageDTOList.size() > 0){
            List<MovieImage> movieImageList = imageDTOList.stream()
                    .map(movieImageDTO -> {
                        MovieImage movieImage = MovieImage.builder()
                                .path(movieImageDTO.getPath())
                                .imgName(movieImageDTO.getImgName())
                                .uuid(movieImageDTO.getUuid())
                                .movie(movie)
                                .build();
                        return movieImage;
                    }).collect(Collectors.toList());

            // Map에 삽입 -> 접근은 imgList로
            entityMap.put("imgList", movieImageList);
        }

        return entityMap; // -> movie, imgList로 각각의 Movie의 정보와 movieImage정보에 접근할 수 있다.
    }
}
