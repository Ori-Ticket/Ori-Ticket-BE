package com.zerobase.study.controller;


import com.zerobase.study.dto.Member;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청 ->(HTML 파일)
// @Controller

//사용자 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {

    @GetMapping("/http/get")
    public String getTest(Member m) {
        return "get 요청 : " + m.getId() + ", " + m.getPassword() + ", " + m.getUsername() + ", " + m.getEmail();
    }

    @PostMapping("/http/post")
    public String postTest(Member m) {
        return "post 요청 : " + m.getId() + ", " + m.getPassword() + ", " + m.getUsername() + ", " + m.getEmail();
    }

    @PostMapping("/http/post1")
    public String postTest1(@RequestBody String text) {
        return "post 요청 : " + text;
    }

    @PutMapping("/http/put")
    public String putTest(Member m) {
        return "put 요청 : " + m.getId() + ", " + m.getPassword() + ", " + m.getUsername() + ", " + m.getEmail();
    }

    @DeleteMapping("/http/delete")
    public String deleteTest(Member m) {
        return "delete 요청 : " + m.getId() + ", " + m.getPassword() + ", " + m.getUsername() + ", " + m.getEmail();
    }
}
