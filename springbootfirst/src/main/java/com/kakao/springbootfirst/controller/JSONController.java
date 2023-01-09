package com.kakao.springbootfirst.controller;

import com.kakao.springbootfirst.dto.ParamDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


// View 대신에 문자열(CSV) 난 JSON을 리턴하게 만들고자 할 때 사용하는 어노테이션
@RestController
// 공통된 URL
@RequestMapping("/api/v1/rest-api")
public class JSONController {
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
    /*@GetMapping("/param3")
    public String getParam(
            ParamDTO paramDTO
    ){
        return "name: " + paramDTO.getName() + ", email: " + paramDTO.getEmail() + ", organization: "
                + paramDTO.getOrganization();
    }*/

    /*@PostMapping("/param4")
    public String getPostParam(@RequestBody ParamDTO paramDTO) {

      return paramDTO.toString();
    }*/


        @PutMapping("/param5")
        public String getPutParam1(@RequestBody ParamDTO paramDTO){
            return paramDTO.toString();
        }

        // 반환 값으로 JSON 문자열을 생성한다.
        @PutMapping("/param6")
        public ParamDTO getPutParam2(@RequestBody ParamDTO paramDTO){
            return paramDTO;
        }

        @PutMapping("/param7")
        // 상태 코드를 설정해서 결과를 반환하는 것이 가능하다.
        public ResponseEntity<ParamDTO> getPutParam3(@RequestBody ParamDTO paramDTO){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(paramDTO);
        }

        @DeleteMapping("/param8")
        public String DeleteNum(@PathVariable("num") int num){
            return num + "";
        }

        @DeleteMapping("/param9")
        public String DeleteParamNum(@PathVariable("num") int num){
            return num + "";
        }

    private final Logger LOGGER =
            LoggerFactory.getLogger(JSONController.class);
    @RequestMapping(value="/logger",method= RequestMethod.GET)
    public String getLogger(){
        LOGGER.info("Hello 요청이 왔습니다.");
        return "Get Hello";
    }

}
