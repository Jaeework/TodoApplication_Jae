package com.example.jae;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // JSON을 리턴하는 웹 서비스임을 명시
public class Hello {

    @GetMapping("/hello") // path 설정, GET 메서드 사용
    public String hello(@RequestParam String name) {
        // 비즈니스 로직
        return "Hello " + name;
    }
}
