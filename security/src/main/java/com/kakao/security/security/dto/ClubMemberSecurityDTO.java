package com.kakao.security.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class ClubMemberSecurityDTO extends User {
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
}
