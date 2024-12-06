package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private QuestionService qService;
    @Autowired
    private AnswerService aService;
    @Autowired
    private UserService uService;

    //@RequestMapping GET과 POST 둘다 가능
    //답변 글쓰기는 인증되지 않으면 요청안되게 (접근불가)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(@PathVariable int id, Model model, Principal principal,
                               @Valid AnswerForm answerForm, BindingResult bindingResult) {
        //질문을 가져온다.
        Question q = this.qService.getQuestion(id);
        SiteUser siteUser = (uService.getUser(principal.getName()));
        if (bindingResult.hasErrors()) {
            model.addAttribute("q",q); //다시 되돌아갈때 질문객체 전달
            return "q_detail";
        }
        //답변을 저장하는 서비스
        this.aService.create(q,answerForm.getContent(),siteUser);
        //답변을 저장한 후 다시 질문 상세 페이지를 요청함.
        return "redirect:/question/detail/" + id;
    }

    //답변 수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modifyAnswer(AnswerForm answerForm, @PathVariable int id, Principal principal) {
        Answer a = this.aService.getAnswer(id);
        if(!a.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"수정권한이 없습니다.");
        }
        answerForm.setContent(a.getContent());
        return "a_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
                               @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        Answer answer = this.aService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.aService.modify(answer, answerForm.getContent());
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }
}
