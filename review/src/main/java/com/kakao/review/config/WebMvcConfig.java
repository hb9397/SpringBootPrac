package com.kakao.review.config;

import com.kakao.review.aop.MeasuringInterceptor;
import org.hibernate.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 웹 설정 클래스임을 명시
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    // 인터셉터 설정 메서드

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new MeasuringInterceptor()) // 적용할 Interceptor Class
                .addPathPatterns("/user"); // 인터셉터가 적용될 URL, /user/* 형식도 가능
    }
}
