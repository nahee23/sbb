package com.mysite.sbb.question;

import com.mysite.sbb.user.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository qRepo;
    //질문들을 모두 가져오는 메서드 (페이지 적용)
    public Page<Question> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createDate").descending()); //현재 페이지와 한페이지 갯수
        return qRepo.findAll(pageable);
    }

    //id로 질문을 가져옴
    public Question getQuestion(int id) {
        Optional<Question> q = qRepo.findById(id);
        if (q.isPresent()) {
            return q.get();
        } else {
            //id로 질문을 못찾았을 경우에 못찾음 예외를 발생시킴
            throw new DataNotFoundException("question not found");
        }
    }
    //새 질문을 저장 (글쓴이도 추가)
    public void createQuestion(String subject, String content, SiteUser author) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(author);
        qRepo.save(q);
    }
    //수정하기
    public void modify(Question q, String subject, String content) {
        q.setSubject(subject);
        q.setContent(content);
        q.setModifyDate(LocalDateTime.now()); //수정시간만 입력
        qRepo.save(q); //id가 있으면 수정
    }

    //삭제하기
    public void deleteQuestion(Question q) {
        qRepo.delete(q);
    }

    //추천하기
    public void vote(Question q, SiteUser siteUser) {
        q.getVoter().add(siteUser);
        this.qRepo.save(q);
    }
}
