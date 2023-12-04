package com.zerobase.oriticket.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    @GeneratedValue
    private Long ticketId;

    private Long sportsId; //
    private Long salePostId; //
    private Long stadiumId; //
    private Long awayTeamId; //

    private int quantity;
    private int salePrice;
    private int originalPrice;

    private LocalDateTime expirationAt;
    private boolean isSuccessive;

    private String seatInfo;
    private String url;
    private String note;

}
