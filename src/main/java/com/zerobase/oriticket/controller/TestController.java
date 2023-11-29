package com.zerobase.oriticket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    // 배포 테스트옹 컨트롤러
    // 삭제 예정

    @GetMapping
    public String test(){
        return "테스트입니다.";
    }
}
