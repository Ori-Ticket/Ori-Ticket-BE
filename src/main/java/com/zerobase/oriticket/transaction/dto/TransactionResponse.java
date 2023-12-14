package com.zerobase.oriticket.transaction.dto;

import com.zerobase.oriticket.transaction.entity.Transaction;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TransactionResponse {

    private Long transactionId;
    private Long salePostId;
    private Long memberId;
    private Integer payAmount;
    private String status;
    private LocalDateTime receivedAt;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    public static TransactionResponse fromEntity(Transaction transaction){
        return TransactionResponse.builder()
                .transactionId(transaction.getTransactionId())
                .salePostId(transaction.getSalePostId())
                .memberId(transaction.getMemberId())
                .payAmount(transaction.getPayAmount())
                .status(transaction.getStatus().getState())
                .receivedAt(transaction.getReceivedAt())
                .startedAt(transaction.getStartedAt())
                .endedAt(transaction.getEndedAt())
                .build();
    }

}
