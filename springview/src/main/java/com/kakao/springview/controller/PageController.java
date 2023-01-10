package com.kakao.springview.controller;

import com.kakao.springview.domain.SampleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class PageController {
    /*@GetMapping("/")
    public String main(Model model) {
        model.addAttribute("message", "Spring Boot 에서의 JSP");
        return "main";
    }*/

    @GetMapping("/")
    public String main(Model model) {
        Map<String, Object> map = new HashMap<>();
        map.put("language", "java");
        map.put("buildtool", "gradle");
        map.put("ide", "intelij");

        // 데이터를 넘길 때 addAttribute 를 사용하면 키-값 의 형태로 넘기면 된다.
        model.addAttribute("map", map);

        List<String> list = new ArrayList<>();
        list.add("Developer");
        list.add("Operator");
        list.add("MLOps");
        list.add("DEVOps");
        list.add("DBA");

        model.addAttribute("list", list);

        return "main";
    }

    // 로거 객체 생성
    private final Logger LOGGER = LoggerFactory.getLogger(PageController.class);
    @GetMapping("/ex1")
    // 반환형이 void 면 출력하는 뷰이름은 요청 URL에 해당 되므로 출력할 view 파일은 ex1
    public void ex1(Model model){
        LOGGER.info("ex1 요청");
    }

    @GetMapping("/ex2")
    public void ex2(Model model){
        List<SampleVO> list = IntStream.range(1, 20).asLongStream().mapToObj(
                i -> {
                    SampleVO vo = SampleVO.builder()
                            .sno(i)
                            .first("First.." + i)
                            .last("Last.." + i)
                            .regTime(LocalDateTime.now())
                            .build();
                    return vo;
                }).collect(Collectors.toList());
        model.addAttribute("list", list);
    }

    @GetMapping({"/exlink", "/exformat"})
    public void exlink(Model model){
        List <SampleVO> list  = new ArrayList<>();
        for(int i=0; i<10; i++){
            SampleVO vo = SampleVO.builder()
                    .sno((long) i)
                    .first("First...")
                    .last("Last...")
                    .regTime(LocalDateTime.now())
                    .build();
            list.add(vo);
        }
        model.addAttribute("list", list);
    }

    // 단순히 레이아웃을 전환
    @GetMapping("/exlayout1")
        public void exlayout(){}

}
