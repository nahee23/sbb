package com.mysite.sbb.answer;

import com.mysite.sbb.question.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository aRepo;
    //해당 질문에 답변을 저장하는 메서드 (유저 추가)
    public void create(Question q, String content, SiteUser author) {
        Answer a = new Answer();
        a.setQuestion(q);
        a.setAuthor(author);
        a.setContent(content);
        a.setCreateDate(LocalDateTime.now());
        aRepo.save(a);
    }

    //답변 조회
    public Answer getAnswer(int id){
        Optional<Answer> a = aRepo.findById(id);
        if(a.isPresent()){
            return a.get();
        } else {
            throw new DataNotFoundException("답변을 찾을 수 없습니다. : "+ id);
        }
    }

    //답변 수정하기
    public void modify(Answer a, String content) {
        a.setContent(content);
        a.setModifyDate(LocalDateTime.now());
        aRepo.save(a);
    }
}
