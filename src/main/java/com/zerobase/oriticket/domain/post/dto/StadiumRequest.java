package com.zerobase.oriticket.domain.post.dto;

import com.zerobase.oriticket.domain.post.entity.AwayTeam;
import com.zerobase.oriticket.domain.post.entity.Sports;
import com.zerobase.oriticket.domain.post.entity.Stadium;
import lombok.Data;

public class StadiumRequest {

    @Data
    public static class Register{
        private Long sportsId;
        private String stadiumName;
        private String homeTeamName;

        public Stadium toEntity(Sports sports){
            return Stadium.builder()
                    .sports(sports)
                    .stadiumName(this.stadiumName)
                    .homeTeamName(this.homeTeamName)
                    .build();
        }

    }
}
