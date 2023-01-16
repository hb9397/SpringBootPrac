package com.kakao.review;

import com.kakao.review.domain.Member;
import com.kakao.review.domain.Movie;
import com.kakao.review.domain.MovieImage;
import com.kakao.review.domain.Review;
import com.kakao.review.persistence.MemberRepository;
import com.kakao.review.persistence.MovieImageRepository;
import com.kakao.review.persistence.MovieRepository;
import com.kakao.review.persistence.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class RepositoryTest {

    // 필드 주입입
   @Autowired
    private MovieRepository movieRepository;

    @Autowired
    MovieImageRepository movieImageRepository;

    @Test
    public void testInsertMovie(){
        // 영화 100개 생성 후 삽입
        IntStream.range(0, 100).forEach(i -> {
            Movie movie = Movie.builder().title("Movie..." + i).build();
            movieRepository.save(movie);

            int count = (int)(Math.random() * 5) + 1;
            for(int j=0; j<count; j++){
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("Test" +j+ ".jpg")
                        .build();
                movieImageRepository.save(movieImage);
            }
        });
    }

    @Autowired
    private MemberRepository memberRepository;
    @Test
    public void testInsertMember(){
        IntStream.range(0, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("test" + i + "@gmail.com")
                    .pw("1111")
                    .nickname("reviewer" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Autowired
    private ReviewRepository reviewRepository;
    @Test
    public void testInsertReview() {
        IntStream.range(0, 200).forEach(i -> {
            // Review 객체에 Movie형 외래키를 만들기 위한 영화 번호 생성
            Long mno = (long)(Math.random() * 100) + 1;

            // Review 객체에 Member형 외래키를 만들기 위한 사용자 번호 생성
            Long mid = (long)(Math.random() * 100) + 1;

            // Review 의 외래키로 사용될 Movie 형 객체 이중 mno
            Movie movie = Movie.builder().mno(mno).build();

            // Review 의 외래키로 사용될 Member 형 객체 이중 mid
            Member member = Member.builder().mid(mid).build();

            Review review = Review.builder()
                    .movie(movie)
                    .member(member)
                    .grade((int)(Math.random()*5) + 1)
                    .text("영화 리뷰는 TEST"+i)
                    .build();
            reviewRepository.save(review);
        });
    }

    @Test
    //JOIN Test
    public void joinTest(){
        Pageable pageable = PageRequest.of(0,10, Sort.Direction.DESC, "mno");
        Page<Object[]> result = movieRepository.getList(pageable);
        for(Object[] objects : result.getContent()){
            System.out.println(Arrays.toString(objects));
        }
    }
}
