package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @RequestMapping("/sbb") //화면을 표시할 ResponseBody 추가해야 웹페이지에서 보임 (콘솔에서는 정삭적으로 출력)
    @ResponseBody //문자열 화면에 출력 (ResponsBody 추가 안하고 url 호출하면 오류 발생)
    public String index() {
        return "안녕하세요 sbb에 오신것을 환영합니다.";
    }
}
