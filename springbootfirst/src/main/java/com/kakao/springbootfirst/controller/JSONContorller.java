package com.kakao.springbootfirst.controller;

import com.kakao.springbootfirst.dto.ParamDTO;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// View 대신에 문자열(CSV) 난 JSON을 리턴하게 만들고자 할 때 사용하는 어노테이션
@RestController
// 공통된 URL
@RequestMapping("/api/v1/rest-api")
public class JSONContorller {
    // 공통된URL/hello URL의 GET 요청 처리
    @RequestMapping(value = "/hello", method= RequestMethod.GET)
    public String getHello(){
        return "Get Hello";
    }
    // Spring 4.3 에서 추가된 요청 어노테이션
    @GetMapping("/newhello")
    public String newHello() {
        return "New Hello";
    }

    @GetMapping("/product/{num}")
    public String getNum(@PathVariable("num") int num){
        return num  + "";
    }

        // HttpServletRequest 를 이용한 파라미터를 가진 GET 요청 처리
    @GetMapping("/param1")
    public String getParam(HttpServletRequest request){
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String organization = request.getParameter("organization");

        return "name: " + name + ", email: " + email + ", organization: " + organization;
    }

    // @RequestParam 을 이용한 파라미터를 가진 GET 요청 처리
    @GetMapping("/param2")
    public String getParam(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("organization") String organization
    ){
        return "name: " + name + ", email: " + email + ", organization: " + organization;
    }

    // Command 객체를 이용한 파라미터를 가진 GET 요청 처리
    @GetMapping("/param3")
    public String getParam(
            ParamDTO paramDTO
    ){
        return "name: " + paramDTO.getName() + ", email: " + paramDTO.getEmail() + ", organization: "
                + paramDTO.getOrganization();
    }
}
