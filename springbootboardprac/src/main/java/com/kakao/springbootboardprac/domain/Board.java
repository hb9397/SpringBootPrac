package com.kakao.springbootboardprac.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "writer") // toString을 만들 때 writer는 제외
@Getter
public class Board extends BaseEntity{
   @Id
   // 기본키 생성 전략을 autoincreament 로
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long bno;
   private String title;
   private String content;

   // 기본적으로 Member 형이기 때문에 Member의 모든 데이터를 한번에 불러온다.
   // 게시판이 N:1 이므로 ManyToOne으로 설정
   // Member 의 기본키인 email 을 들고와서 writer_email로 외래키가 설정된다.
   @ManyToOne(fetch = FetchType.LAZY)
   // 관계설정에 fetch = FetchType.LAZY 를 설정하면 처음에는 가져오지 않고 사용할 때 외래키 정보를 참조해서 가져온다.
   private Member writer;

   // 수정이 가능한 속성을 수정시키기 위한 메서드 추가

   // title을 수정하는 메서드
   public void changeTitle(String title){
      if(title == null || title.trim().length() == 0){
         this.title="무제";
         return;
      }
      this.title = title;
   }

   // content를 수정하는 메서드
   public void changeContent(String content){
      if(content == null || title.trim().length() == 0){
         this.content="내용없음";
         return;
      }
      this.content = content;
   }
}
