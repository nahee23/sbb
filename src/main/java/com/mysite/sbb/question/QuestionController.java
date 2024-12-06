package com.mysite.sbb.question;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService qService;
    @Autowired
    private UserService uService;

    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Question> paging = this.qService.getList(page);
        model.addAttribute("paging", paging);
        return "q_list"; //return = forward
    }

    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable int id, Model model, AnswerForm answerForm) {
        //@PathVariable 주소 변수
        //서비스로 질문내용을 가져옴
        //질문상세페이지에서도 AnswerForm 을 받아와야함????
        Question q = this.qService.getQuestion(id);
        model.addAttribute("q", q);
        return "q_detail";
    }

    //질문 글쓰기는 인증되지 않으면 요청안되게 (접근불가)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create(QuestionForm questionForm) {
        return "q_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String qCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        // @Vaild 로 해당 객체를 검사한다. 결과는 두번째 나오는 bindingResult 에 저장됨
        if (bindingResult.hasErrors()) {
            return "q_form";
        }
        SiteUser siteUser = uService.getUser(principal.getName());
        qService.createQuestion(questionForm.getSubject(), questionForm.getContent(),siteUser);
        return "redirect:/question/list";

    }

    //수정 : 인증된 유저
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable int id, Principal principal, QuestionForm questionForm) {
        Question q = this.qService.getQuestion(id);
        if (!q.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }
        questionForm.setSubject(q.getSubject());
        questionForm.setContent(q.getContent());
        return "q_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable int id) {
        if (bindingResult.hasErrors()) {
            return "q_form"; //되돌아감
        }
        Question q = this.qService.getQuestion(id);
        if (!q.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }
        this.qService.modify(q, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);

    }

    //삭제하기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Principal principal) {
        Question q = this.qService.getQuestion(id);
        if (!q.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.qService.deleteQuestion(q);
        return "redirect:/";
    }

    //추천하기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String voidQuestion(@PathVariable int id, Principal principal) {
        Question q = this.qService.getQuestion(id);
        SiteUser siteUser = uService.getUser(principal.getName());
        this.qService.vote(q, siteUser);
        return "redirect:/question/detail/"+id;
    }
}
