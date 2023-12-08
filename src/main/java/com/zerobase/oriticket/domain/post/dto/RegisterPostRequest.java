package com.zerobase.oriticket.domain.post.dto;

import com.zerobase.oriticket.domain.post.constants.SaleStatus;
import com.zerobase.oriticket.domain.post.entity.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterPostRequest {

    private Long memberId;
    private Long sportsId;
    private Long stadiumId;
    private Long awayTeamId;

    private Integer quantity;
    private Integer salePrice;
    private Integer originalPrice;

    private LocalDateTime expirationAt;
    private Boolean isSuccessive;

    private String seatInfo;
    private String imgUrl;
    private String note;

    public Post toEntityPost(Ticket ticket) {
        return Post.builder()
                .memberId(memberId)
                .ticket(ticket)
                .saleStatus(SaleStatus.FOR_SALE)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public Ticket toEntityTicket(Sports sports, Stadium stadium, AwayTeam awayTeam){
        return Ticket.builder()
                .sports(sports)
                .stadium(stadium)
                .awayTeam(awayTeam)
                .quantity(this.quantity)
                .salePrice(this.salePrice)
                .originalPrice(this.originalPrice)
                .expirationAt(this.expirationAt)
                .isSuccessive(this.isSuccessive)
                .seatInfo(this.seatInfo)
                .imgUrl(this.imgUrl)
                .note(this.note)
                .build();
    }

}
