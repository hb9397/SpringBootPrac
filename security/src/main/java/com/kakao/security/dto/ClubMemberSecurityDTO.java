package com.kakao.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
public class ClubMemberSecurityDTO extends User implements OAuth2User {
    private String mid;

    private String mpw;

    private String email;

    private String name;

    private boolean del;

    private boolean social;


    // User를 상속받는 해당 클래스의 생성자 작성
    // Collection<? extends GrantedAuthority> 사용하면 생성자의 매개변수 순서가 같아야한다.
    public ClubMemberSecurityDTO(String username, String password, String email, String name, boolean del, boolean social,
                                 Collection<? extends GrantedAuthority> authorities
                                 ) {
        super(username, password, authorities);

        this.mid = username;
        this.mpw = password;
        this.email = email;
        this.name = name;
        this.del = del;
        this.social = social;
    }

    // 소셜 로그인 시 필요한 코드
    private Map<String, Object> props;
    @Override
    public Map<String, Object> getAttributes() {
        return this.getProps();
    }

    @Override
    public String getName() {
        return this.mid;
    }
}
