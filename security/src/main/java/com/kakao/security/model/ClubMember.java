package com.kakao.security.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

@Log4j2
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString(exclude = "roleSet")
@Entity
public class ClubMember extends BaseEntity{

    @Id
    private String mid;

    private String mpw;

    private String email;

    private String name;

    private boolean del;

    private boolean social;

    // 권한은 여러개 소유하되 사용자면 사용자 1개 관리자면 관리자 1개 만을 갖는것으로 사용자 권한을 여러개 중복해서 받지는 않는다.
    // 따라서 자료형은 set(중복X) 으로 하는 것을 권장한다.

    @ElementCollection(fetch = FetchType.LAZY) // 지연 로딩 사용
    // 관계형 DB 에서는 SET, LIST 와 같은 자료는 없기 때문에 별도의 테이블을 만들어서 보여준다.
    @Builder.Default
    private Set<ClubMemberRole> roleSet = new HashSet<>();


    // Entity 속성중 수정이 가능한 것들
    public void changePassword(String mpw){
        this.mpw = mpw;
    }

    public void changeEmail (String email){
        this.email= email;
    }

    public void changeDel(boolean del){
        this.del = del;
    }

    public void addRole(ClubMemberRole memberRole){
        this.roleSet.add(memberRole);
    }

    // 권한 삭제
    public void clearRoles(){
        this.roleSet.clear();
    }

    public void changeSocial(boolean social){
        this.social = social;
    }
}
