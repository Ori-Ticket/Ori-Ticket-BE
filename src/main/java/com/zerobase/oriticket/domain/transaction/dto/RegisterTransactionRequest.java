package com.zerobase.oriticket.domain.transaction.dto;

import com.zerobase.oriticket.domain.post.entity.Post;
import com.zerobase.oriticket.domain.transaction.constants.TransactionStatus;
import com.zerobase.oriticket.domain.transaction.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterTransactionRequest {
    private Long salePostId;
    private Long memberId;

    public Transaction toEntity(Post salePost) {
        return Transaction.builder()
                .salePost(salePost)
                .memberId(this.memberId)
                .status(TransactionStatus.PENDING)
                .startedAt(LocalDateTime.now())
                .build();
    }
}
