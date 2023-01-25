package com.kakao.security.config;

import com.kakao.security.security.CustomUserDetailService;
import com.kakao.security.security.handler.Custom403Handler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Log4j2
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
// 설정파일을 만들기 위한 애노테이션 or Bean을 등록하기 위한 애노테이션
@Configuration
public class CustomSecurityConfig {

    private final DataSource dataSource;

    private final CustomUserDetailService userDetailService;

    @Bean
    public PersistentTokenRepository persistenceTokenRepository(){
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);

        return repo;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new Custom403Handler();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        log.info("필터 환경 설정");

        // 인증이나 인가에 문제가 발생하면 로그인 폼 출력
        httpSecurity.formLogin().loginPage("/member/login");

        // CSRF 토큰 비활성화
        httpSecurity.csrf().disable();

        // 자동로그인 remeber-me 에 대한 설정
        httpSecurity.rememberMe()
                .key("12345678")
                .tokenRepository(persistenceTokenRepository())
                .userDetailsService(userDetailService)
                .tokenValiditySeconds(60*60*24*30);

        // 403 에러 발생시 커스텀 헨들러 호출
        httpSecurity.exceptionHandling().accessDeniedHandler(accessDeniedHandler());

        // OAuth2 가 사용할 로그인 URL 설정
        httpSecurity.oauth2Login().loginPage("/member/login");

        return httpSecurity.build();
    }

    // 정적 파일 요청은 동작하지 않도록 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return(web) ->
            web.ignoring()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations());

    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
