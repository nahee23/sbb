package com.mysite.sbb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor //기본생성자
@AllArgsConstructor //전체 입력변수 생성자
public class HelloLombok {

    private String hello;
    private int lombok;

    public static void main(String[] args) {
        HelloLombok hello = new HelloLombok();
        HelloLombok hello2 = new HelloLombok("헬로",456); //전체 입력변수 생성자?
        hello.setHello("Hello");
        hello.setLombok(123);
        System.out.println(hello.getHello());
        System.out.println(hello.getLombok());
        System.out.println(hello2.getHello());
        System.out.println(hello2.getLombok());

    }
}
