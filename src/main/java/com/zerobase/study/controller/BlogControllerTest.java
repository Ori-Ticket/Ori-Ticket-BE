package com.zerobase.study.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogControllerTest {

    @GetMapping("/loginPage")
    public String loginPage() {
        return "oriticket/user/loginPage";
    }

}
