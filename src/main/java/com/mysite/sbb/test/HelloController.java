package com.mysite.sbb.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//서블릿 즉 컨트롤러 역할
@Controller
public class HelloController {

    //localhost:8080/hello
    @RequestMapping("/hello")
    @ResponseBody //기본 mvc 는 컨트롤러가 화면을 전달하는데 여기서는 바로 표시
    public String hello() {
        return "Hello World";

    }

    @RequestMapping("/hi")
    @ResponseBody //기본 mvc 는 컨트롤러가 화면을 전달하는데 여기서는 바로 표시
    public String hi() {
        return "안녕!";

    }

    @RequestMapping("/hi4")
    @ResponseBody //기본 mvc 는 컨트롤러가 화면을 전달하는데 여기서는 바로 표시
    public String hi4() {
        return "안녕4!";

    }
}
