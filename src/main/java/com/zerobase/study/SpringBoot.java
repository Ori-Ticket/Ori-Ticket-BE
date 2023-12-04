package com.zerobase.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication  //(exclude = DataSourceAutoConfiguration.class)
public class SpringBoot {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot.class, args);
    }
}
