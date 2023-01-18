package com.kakao.review.service;

import com.kakao.review.domain.Movie;
import com.kakao.review.domain.MovieImage;
import com.kakao.review.dto.MovieDTO;
import com.kakao.review.dto.MovieImageDTO;
import com.kakao.review.dto.PageRequestDTO;
import com.kakao.review.dto.PageResponseDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public interface MovieService {
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

    // 검색 결과를 DTO로 변환해주는 메서드
    default MovieDTO entityToDTO(Movie movie, List<MovieImage> movieImages, double avg, Long reviewCnt){

        // 영화 정보
        MovieDTO movieDTO = MovieDTO.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .regDate(movie.getRegDate())
                .modDate(movie.getModDate())
                .build();

        // 이미지 목록
        List<MovieImageDTO> movieImageDTOList = movieImages.stream().map(movieImage -> {
            return MovieImageDTO.builder()
                    .imgName(movieImage.getImgName())
                    .path(movieImage.getPath())
                    .uuid(movieImage.getUuid())
                    .build();
        }).collect(Collectors.toList());

        // MovieDTO의 imageDTOList 속성에 MovieImages 를 변환한 DTO 로 set
        movieDTO.setImageDTOList(movieImageDTOList);

        movieDTO.setAvg(avg);

        movieDTO.setReviewCnt(reviewCnt);

        return movieDTO;
    }

    // 데이터 삽입을 위한 메서드
    Long register(MovieDTO movieDTO);

    // 데이터 목록을 위한 메서드
    PageResponseDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO);

    // 상세보기를 위한 메서드
    MovieDTO getMovie(Long mno);
}
