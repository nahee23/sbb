package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

//엔티티는 JPA의 테이블과 같은 클래스
@Entity
@Getter
@Setter
@ToString
public class Question {

    //기본키 열 id, 자동으로 1증가 옵션
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //제목열은 200자까지
    @Column (length = 200)
    private String subject;

    @Column (columnDefinition = "TEXT")
    private String content;

    //등록 시간
    private LocalDateTime createDate;

    //수정시간
    private LocalDateTime modifyDate;

    //외래키 설정
    @ManyToOne
    private SiteUser author;

    //반대로 이 질문에 해당 답변들 (답변은 한글에 여러개라서 LIST 그리고 @OneToMany)
    //질문 삭제되면 답변도 같이 삭제
    //DB에서는 보이지 않음
    @OneToMany (mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;


}
