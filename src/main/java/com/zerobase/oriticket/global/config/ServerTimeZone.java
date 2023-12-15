package com.zerobase.oriticket.global.config;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerTimeZone {
    @PostConstruct
    public void setServerTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

}