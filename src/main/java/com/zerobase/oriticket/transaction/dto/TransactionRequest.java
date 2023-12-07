package com.zerobase.oriticket.transaction.dto;

import com.zerobase.oriticket.transaction.constants.TransactionStatus;
import com.zerobase.oriticket.transaction.entity.Transaction;
import lombok.Data;

import java.time.LocalDateTime;

public class TransactionRequest {

    @Data
    public static class Register{
        private Long salePostId;
        private Long memberId;

        public Transaction toEntity(Long salePostId, Long memberId) {
            return Transaction.builder()
                    .salePostId(salePostId)
                    .memberId(memberId)
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
