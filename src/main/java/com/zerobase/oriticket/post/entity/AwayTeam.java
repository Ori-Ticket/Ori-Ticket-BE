package com.zerobase.oriticket.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AwayTeam {

    @Id
    @GeneratedValue
    private Long awayTeamId;

    private Long sportsId; //

    private String awayTeamName;

}
