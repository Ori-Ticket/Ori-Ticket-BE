package com.zerobase.oriticket.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long awayTeamId;

    @ManyToOne
    @JoinColumn(name = "SPORTS_ID")
    private Sports sports;

    private String awayTeamName;

}
