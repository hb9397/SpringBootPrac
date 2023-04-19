package com.example.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j(topic = "kafka-logger")
public class LogController {
    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable String name){
        log.info("hello~!@" + name);
        return "Hello " + name;
    }

    @GetMapping("/goodbye/{name}")
    public String goodbye(@PathVariable String name){
        log.warn("goodbye~!@" + name);
        return "Good Bye  " + name;
    }
}
