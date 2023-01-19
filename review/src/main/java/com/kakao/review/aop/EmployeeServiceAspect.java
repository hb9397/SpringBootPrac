package com.kakao.review.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component // Bean을 자동으로 생성해주는 어노테이션
// Controller, Service, Repository, RestController, Configuration
public class EmployeeServiceAspect {

    // 메서드가 호출 되기 이전에 수행
    @Before(value = "execution(* com.kakao.review.service.EmployeeService.*(..)) && args(empId, fname, sname)")
    public void beforeAdvice(JoinPoint joinPoint, String empId, String fname, String sname){
        System.out.println("메서드 호출 이전");
    }

    // 메서드가 호출된 이후에 수행
    @After(value = "execution(* com.kakao.review.service.EmployeeService.*(..)) && args(empId, fname, sname)")
    public void afterAdvice(JoinPoint joinPoint, String empId, String fname, String sname){
        System.out.println("메서드 호출 하고 수행된 이후에 수행");
    }
}
