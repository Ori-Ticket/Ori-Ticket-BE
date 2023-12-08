package com.zerobase.oriticket.domain.transaction.dto;

import com.zerobase.oriticket.domain.post.entity.Post;
import com.zerobase.oriticket.domain.transaction.constants.TransactionStatus;
import com.zerobase.oriticket.domain.transaction.entity.Transaction;
import lombok.Data;

import java.time.LocalDateTime;

public class TransactionRequest {

    @Data
    public static class Register{
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

    @Data
    public static class UpdateStatus{
        private Long transactionId;
    }
}