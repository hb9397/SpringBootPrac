package com.kakao.review.service;

import com.kakao.review.domain.Movie;
import com.kakao.review.domain.Review;
import com.kakao.review.dto.ReviewDTO;
import com.kakao.review.persistence.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor // 레포지토리 생성자 주입
@Service
public class ReviewServiceImpl implements ReviewService{

    // 레포지토리 주입
    private final ReviewRepository reviewRepository;

    // 특정 영화의 리뷰 조회
    @Override
    public List<ReviewDTO> getList(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();
        List<Review> result = reviewRepository.findByMovie(movie);

        return result.stream().map(review -> entityToDto(review)).collect(Collectors.toList());
    }

    // 리뷰 등록
    @Override
    public Long register(ReviewDTO reviewDTO) {
        Review review = dtoToEntity(reviewDTO);

        reviewRepository.save(review);

        return review.getReviewnum();
    }

    // 리뷰 수정
    @Override
    public Long modify(ReviewDTO reviewDTO) {
        // 삽입과 형식이 똑같은데 글번호가 DB에 있는지 없는지로 나뉘므로 한 번 검사하고 하는 것을 권장하기도 한다.
        Review review = dtoToEntity(reviewDTO);
        reviewRepository.save(review);
        return review.getReviewnum();
    }

    // 리뷰 삭제
    @Override
    public Long remove(Long runm) {
        reviewRepository.deleteById(runm);
        return runm;
    }
}
