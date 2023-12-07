package com.zerobase.oriticket.domain.post.dto;

import com.zerobase.oriticket.domain.post.constants.SaleStatus;
import com.zerobase.oriticket.domain.post.entity.Post;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse {

    private Long memberId;
    private TicketResponse ticketResponse;
    private SaleStatus saleStatus;
    private LocalDateTime createdAt;

    public static PostResponse fromEntity(Post post) {

        TicketResponse ticketResponse = TicketResponse.fromEntity(post.getTicket());

        return PostResponse.builder()
                .memberId(post.getMemberId())
                .ticketResponse(ticketResponse)
                .saleStatus(post.getSaleStatus())
                .createdAt(post.getCreatedAt())
                .build();
    }

}
