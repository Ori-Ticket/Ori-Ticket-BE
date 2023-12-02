package com.zerobase.oriticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OriticketApplication {

    public static void main(String[] args) {
        SpringApplication.run(OriticketApplication.class, args);
    }

}
