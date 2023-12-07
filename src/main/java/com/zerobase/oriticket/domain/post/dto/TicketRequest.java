package com.zerobase.oriticket.domain.post.dto;

import com.zerobase.oriticket.domain.post.entity.AwayTeam;
import com.zerobase.oriticket.domain.post.entity.Stadium;
import java.time.LocalDateTime;

import com.zerobase.oriticket.domain.post.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketRequest {

    private Long sportsId;
    private Long salePostId;
    private Stadium stadiumId;
    private AwayTeam awayTeamId;

    private int quantity;
    private int salePrice;
    private int originalPrice;

    private LocalDateTime expirationAt;
    private boolean isSuccessive;

    private String seatInfo;
    private String imgUrl;
    private String note;

    public Ticket toEntity(){

    }

    public TicketRequest toTicketRequest(PostRequest.Register request){

    }

}
