package com.kakao.security;

import com.kakao.security.model.ClubMember;
import com.kakao.security.model.ClubMemberRole;
import com.kakao.security.persistence.ClubMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    // @RequiredArgsConstructor 와 final 키워드를 이용해서 사용하지 않는 이유는 생성자 주입을 사용해야 하는데
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClubMemberRepository clubMemberRepository;

    // 샘플 데이터 삽입
    @Test
    public void insertMembers(){
        IntStream.range(1, 100).forEach(i -> {
            ClubMember clubMember = ClubMember.builder()
                    .mid("member"+ i)
                    .mpw(passwordEncoder.encode("1111"))
                    .email("user"+i+"@gmail.com")
                    .social(false)
                    .name("사용자" + i)
                    .roleSet(new HashSet<ClubMemberRole>())
                    .build();

            clubMember.addRole(ClubMemberRole.USER);
            if(i > 90){ // 90 번 이상 부터는 두개의 권한을 갖도록 한다.
                clubMember.addRole(ClubMemberRole.ADMIN);
            }
            clubMemberRepository.save(clubMember);
        });
    }

    @Test
    // 사용자 정보를 가져올 때 사용자의 권한도 같이 가져오는 메서드
    public void testRead(){
        // Optional<ClubMember> result = clubMemberRepository.getWithRoles("member100"); // DB 에 없는 사용자
        Optional<ClubMember> result = clubMemberRepository.getWithRoles("member97"); // DB 에 존재하는 사용자
        if(result.isPresent()){
            System.out.println(result);
            System.out.println(result.get().getRoleSet());
        } else {
            System.out.println("존재하지 않는 아이디 입니다.");
        }
    }

    // 소셜 로그인시 email로 로그인 여부 판단하는 메서드
    @Test
    public void testReadEmail(){
        Optional<ClubMember> clubMember = clubMemberRepository.findByEmail("user97@gmail.com");
        System.out.println(clubMember.get().getRoleSet());
    }
}
