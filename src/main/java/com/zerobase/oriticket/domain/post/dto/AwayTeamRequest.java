package com.zerobase.oriticket.domain.post.dto;

import com.zerobase.oriticket.domain.post.entity.AwayTeam;
import com.zerobase.oriticket.domain.post.entity.Sports;
import lombok.Data;

public class AwayTeamRequest {

    @Data
    public static class Register{
        private Long sportsId;
        private String awayTeamName;

        public AwayTeam toEntity(Sports sports){
            return AwayTeam.builder()
                    .sports(sports)
                    .awayTeamName(awayTeamName)
                    .build();
        }
    }
}
