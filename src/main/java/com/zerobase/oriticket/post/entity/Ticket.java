package com.zerobase.oriticket.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @ManyToOne
    @JoinColumn(name = "SPORTS_ID")
    private Sports sports;

    @ManyToOne
    @JoinColumn(name = "STADIUM_ID")
    private Stadium stadium;

    @ManyToOne
    @JoinColumn(name = "AWAY_TEAM_ID")
    private AwayTeam awayTeam;

    private int quantity;
    private int salePrice;
    private int originalPrice;

    private LocalDateTime expirationAt;
    private boolean isSuccessive;

    private String seatInfo;
    private String url;
    private String note;

}
