package com.mysite.sbb.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {

    @NotBlank(message = "내용은 필수항목입니다.")
    @Size(min = 3, message = "최소 3자 이상 작성해야합니다.")
    private String content;

}
