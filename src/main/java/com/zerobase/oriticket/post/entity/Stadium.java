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
public class Stadium {

    @Id
    @GeneratedValue
    private Long stadiumId;

    private Long sportsId; //

    private String stadiumName;
    private String homeTeamName;

}
