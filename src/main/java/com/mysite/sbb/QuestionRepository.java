package com.mysite.sbb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//JpaRepository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    //JPA 는 CRUD 구현 클래스가 자동으로 완성되어 객체가 생성됨
    Question findBySubject(String subject);
    Question findBysubjectAndContent(String subject, String content);
    List<Question> findBySubjectContaining(String subject);

}
