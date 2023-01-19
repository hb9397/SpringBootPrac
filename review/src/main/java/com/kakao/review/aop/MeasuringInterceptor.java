package com.kakao.review.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
public class MeasuringInterceptor implements HandlerInterceptor {

    @Override
    // Controller에게 요청을 하기 전에 호출되는 메서드
    // false로 반환하면 Controller에게 요청을 전달하지 않는다.
    // false하고 끝내는 것이 아니라 false를 보낼 때는 항상 어딘가로 갈 수 있게 redirect해야 한다.
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler){
        log.warn("Controller 가 요청을 처리하기 전에 호출");
        return true;
    }

    // Controller가 요청을 정상적으로 처리한 후 의 메서드
    // 위치나 접속 장치에 따라 로그인 할 때, 변화된 위치나 장치에 대해 알람을 주는 것도 이 영역에서 처리한다.
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView){
        // 로그 기록
        log.warn("요청을 정상적으로 처리한 후 호출");

    }

    @Override
    // Controller가 요청을 처리한 후 무조건 호출되는 메서드(예외처리 포함)
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception e){

        log.warn("비정상적으로 처리되도 호출");
    }
}
