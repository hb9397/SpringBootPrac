package com.kakao.springbootjpaprac;

import com.kakao.springbootjpaprac.persistence.MemoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBatisTest {
    @Autowired
    MemoMapper memoMapper;

    @Test
    public void mybatis() {
        System.out.println("씨,발");
        System.out.println(memoMapper);
        System.out.println(memoMapper);
    }
}
