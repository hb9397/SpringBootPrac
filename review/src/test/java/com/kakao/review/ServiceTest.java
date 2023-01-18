package com.kakao.review;

import com.kakao.review.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTest {
    // 필드 주입
    @Autowired
    private MovieService movieService;

    @Test
    public void getDetailTest(){
        System.out.println(movieService.getMovie(24L));
    }
}
