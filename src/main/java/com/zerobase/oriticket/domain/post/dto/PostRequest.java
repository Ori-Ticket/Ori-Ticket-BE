package com.zerobase.oriticket.domain.post.dto;

import com.zerobase.oriticket.domain.post.constants.SaleStatus;
import com.zerobase.oriticket.domain.post.entity.AwayTeam;
import com.zerobase.oriticket.domain.post.entity.Post;
import com.zerobase.oriticket.domain.post.entity.Stadium;
import com.zerobase.oriticket.domain.post.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequest {

    @Data
    public static class Register{
        private Long memberId;
        private Long sportsId;
        private Long stadiumId;
        private Long awayTeamId;

        private int quantity;
        private int salePrice;
        private int originalPrice;

        private LocalDateTime expirationAt;
        private boolean isSuccessive;

        private String seatInfo;
        private String imgUrl;
        private String note;

        public Post toEntity() {
            return Post.builder()
                    .memberId(memberId)
                    .ticket(ticket)
                    .saleStatus(SaleStatus.FOR_SALE)
                    .createdAt(LocalDateTime.now())
                    .build();
        }
    }


}
