package com.zerobase.oriticket.post.dto;

import com.zerobase.oriticket.post.entity.AwayTeam;
import com.zerobase.oriticket.post.entity.Stadium;
import java.time.LocalDateTime;
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
    private String url;
    private String note;

}
