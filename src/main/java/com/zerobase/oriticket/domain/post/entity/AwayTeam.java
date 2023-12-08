package com.zerobase.oriticket.domain.post.entity;

import jakarta.persistence.*;
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
