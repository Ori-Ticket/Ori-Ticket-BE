package com.zerobase.oriticket.post.dto;

import com.zerobase.oriticket.post.constants.SaleStatus;
import com.zerobase.oriticket.post.entity.Post;
import com.zerobase.oriticket.post.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequest {

    private Long memberId;
    private Long ticketId;

    // 추후 멤버로 수정
    public Post toEntity(Long memberId, Ticket ticket) {
        return Post.builder()
                .memberId(memberId)
                .ticket(ticket)
                .saleStatus(SaleStatus.FOR_SALE)
                .build();
    }
}
