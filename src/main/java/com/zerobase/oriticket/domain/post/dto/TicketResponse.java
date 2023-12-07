package com.zerobase.oriticket.domain.post.dto;

import com.zerobase.oriticket.domain.post.entity.Ticket;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TicketResponse {

    private Long sportsId;
    private Long stadiumId;
    private Long awayTeamId;

    private int quantity;
    private int salePrice;
    private int originalPrice;

    private LocalDateTime expirationAt;
    private boolean isSuccessive;

    private String seatInfo;
    private String url;
    private String note;

    public static TicketResponse fromEntity(Ticket ticket) {
        return TicketResponse.builder()
                .sportsId(ticket.getSports().getSportsId())
                .stadiumId(ticket.getStadium().getStadiumId())
                .awayTeamId(ticket.getAwayTeam().getAwayTeamId())
                .quantity(ticket.getQuantity())
                .salePrice(ticket.getSalePrice())
                .originalPrice(ticket.getOriginalPrice())
                .expirationAt(ticket.getExpirationAt())
                .isSuccessive(ticket.isSuccessive())
                .seatInfo(ticket.getSeatInfo())
                .url(ticket.getUrl())
                .note(ticket.getNote())
                .build();
    }

}
