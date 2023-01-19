package com.kakao.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTest {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void testEncode(){
        String password = "1111";
        String enPw = passwordEncoder.encode(password);
        System.out.println("enPw" + enPw);

        enPw = passwordEncoder.encode(password);
        System.out.println("enPw" + enPw);

        // 해싱된 비밀번호와 그렇지 않은 비밀번호를 비교 할 수 있다.
        boolean result = passwordEncoder.matches(password, enPw);
        System.out.println("Result: "+ result);
    }
}
