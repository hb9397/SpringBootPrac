package com.kakao.review.service;

import com.kakao.review.domain.Movie;
import com.kakao.review.domain.MovieImage;
import com.kakao.review.dto.MovieDTO;
import com.kakao.review.persistence.MovieImageRepository;
import com.kakao.review.persistence.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieImageRepository movieImageRepository;

    @Override
    // movie와 movieimage 각각의 dto를 entity 로 바꿔주는 인터페이스의 default 메서드를 이용해 영화 이미지와 영화 정보를 하나의 서비스로 만들지만
    // 이미지와 정보는 실제로 다른 엔티티로 나뉘어 다른 테이블에 삽입 된다.
    // 하지만 사용자 입장에서는 하나의 서비스 이므로 둘 중에 하나만 성공/실패 하는 경우가 있으면 안되므로
    // 트랜젝션을 설정해 둘다 성공하거나 둘다 실패하게 해야 한다.
    @Transactional
    public  Long register(MovieDTO movieDTO) {
        log.warn("movieDTO :" + movieDTO);

        // Map에 String(키) - Object(값) 으로 각각 movieDTO, movieImageDTO에 접근해서 값을 가져올 수있다.
        // 두 값모두 DTO가 Entity로 변환되어 있는 것
        Map<String, Object > entityMap = dtoToEntity(movieDTO);

        // 영화와 영화 이미지 정보 찾아오기
        // Map에 있는 영화 정보를 Movie Entity로 가져온다.
        Movie movie = (Movie)entityMap.get("movie");

        // Map에 있는 영화 이미지 리스트(영화정보 하나에 여러 영화 이미지를 가질 수 있다.)
        // 여러개의 이미지가 있을 수 있으므로 List로 받아온다.
        List<MovieImage> movieImageList = (List<MovieImage>)entityMap.get("imgList");

        // 영화 정보를 movieRepository를 이용해서 DB로 보낸다.
        movieRepository.save(movie);


        // List에서 하나하나의 영화 이미지를 꺼내서 DB에 삽입
        movieImageList.forEach(movieImage -> {
            movieImageRepository.save(movieImage);
        });

        return movie.getMno();
    }
}
