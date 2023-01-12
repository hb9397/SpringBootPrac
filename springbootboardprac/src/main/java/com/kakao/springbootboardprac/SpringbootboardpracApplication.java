package com.kakao.springbootboardprac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//JPA 변화 감시 옵션
@EnableJpaAuditing
public class SpringbootboardpracApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootboardpracApplication.class, args);
	}

}
