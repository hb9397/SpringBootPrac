package com.kakao.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Log4j2
@RequiredArgsConstructor
// 설정파일을 만들기 위한 애노테이션 or Bean을 등록하기 위한 애노테이션
@Configuration
public class CustomSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        log.info("필터 환경 설정");
        return http.build();
    }

    // 정적 파일 요청은 동작하지 않도록 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return(web) ->
            web.ignoring()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations());

    }
}
