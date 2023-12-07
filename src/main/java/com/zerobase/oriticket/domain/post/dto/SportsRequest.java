package com.zerobase.oriticket.domain.post.dto;

import com.zerobase.oriticket.domain.post.entity.Sports;
import lombok.Data;

public class SportsRequest {

    @Data
    public static class Register{
        private String sportsName;

        public Sports toEntity(){
            return Sports.builder()
                    .sportsName(this.sportsName)
                    .build();
        }
    }
}
