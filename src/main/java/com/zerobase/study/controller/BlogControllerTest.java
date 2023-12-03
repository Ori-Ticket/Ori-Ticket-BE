package com.zerobase.study.controller;


import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogControllerTest {

    @GetMapping("test/hello")
    public String hello() {
        return "<h1>hello spring boot</h1>";
    }

}
