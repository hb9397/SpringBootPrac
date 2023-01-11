package com.kakao.guestbook;

import com.kakao.guestbook.domain.GuestBook;
import com.kakao.guestbook.domain.QGuestBook;
import com.kakao.guestbook.persistence.GuestBookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.stream.IntStream;

@SpringBootTest
public class RepositoryTest {
    @Autowired
    private GuestBookRepository guestBookRepository;

    // 데이터 삽입 메서드 테스트
    @Test
    public void insertDummies(){
        IntStream.range(1, 300).forEach(i -> {
            GuestBook guestBook = GuestBook.builder()
                    .title("제목.." + i)
                    .content("내용.." + i)
                    .writer("user" + i)
                    .build();
            System.out.println(guestBookRepository.save(guestBook));
        });
    }

    // 데이터 수정 메서드
    @Test
    public void updateDummies(){
        GuestBook guestBook = GuestBook.builder()
                .gno(1L)
                .title("제목 변경")
                .content("내용 변경")
                .writer("누구게")
                .build();
        System.out.println(guestBookRepository.save(guestBook));
    }

    @Test
    // title에 1이라는 글자가 포함된 Entity조회
    public void testQueryDSL1(){
        // moddate 의 내림차순 정렬된 데이터 셋을 10개 씩 나눈 첫번째 페이지가 pageable 인스턴스
        Pageable pageable = PageRequest.of(0, 10, Sort.by("modDate").descending());

        // QueryDSL 수행
        QGuestBook qGuestBook = QGuestBook.guestBook;

        // title에 1이 포함된 조건을 생성
        String keyword="1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qGuestBook.title.contains(keyword);
        builder.and(expression);

        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);

        for(GuestBook guestBook : result.getContent()){
            System.out.println(guestBook);
        }
    }

    //      title과 content에 1이 포함되고 gno가 100 보다 작은 것을 10개씩 나눈 것의 첫번째 페이지 만큼만  조회
    @Test
    public void testQueryDSL2(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("modDate").descending());

        // QueryDSL 수행
        QGuestBook qGuestBook = QGuestBook.guestBook;

        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();

        // title에 포함된 조건
        BooleanExpression exTitle = qGuestBook.title.contains(keyword);

        // content 에 포함된 조건
        BooleanExpression exContent = qGuestBook.content.contains(keyword);

        // title과 content 의 조건을 or로 결합
        BooleanExpression exAll = exTitle.or(exContent);

        // boolean builder 를 위의 조건으로 생성
        builder.and(exAll);

        // boolean builder 에 gno가 100보다 작은 것만 가져오는 조건 추가해서 생성
        builder.and(qGuestBook.gno.lt(100L));

        // 빌더 조건과 페이징 조건에 해당되는 데이터만 result로
        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);

        // result 내용을 result.getContent 만큼 guestbook으로 담아서 출력
        for (GuestBook guestBook : result.getContent()){
            System.out.println(guestBook);
        }
    }
}
