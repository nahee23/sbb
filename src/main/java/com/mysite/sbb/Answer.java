package com.mysite.sbb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    //질문엔티티 참조 @ManyToOne (외래키)
    @ManyToOne
    private Question question;
}
