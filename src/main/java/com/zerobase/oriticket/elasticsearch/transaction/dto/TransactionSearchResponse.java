package com.zerobase.oriticket.elasticsearch.transaction.dto;

import com.zerobase.oriticket.elasticsearch.transaction.entity.TransactionSearchDocument;
import com.zerobase.oriticket.transaction.constants.TransactionStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Builder
@Getter
public class TransactionSearchResponse {

    private Long transactionId;
    private Long salePostId;
    private String memberName;
    private Integer payAmount;
    private String status;
    private LocalDateTime receivedAt;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    public static TransactionSearchResponse fromEntity(TransactionSearchDocument transactionSearchDocument){
        return TransactionSearchResponse.builder()
                .transactionId(transactionSearchDocument.getTransactionId())
                .salePostId(transactionSearchDocument.getSalePostId())
                .memberName(transactionSearchDocument.getMemberName())
                .payAmount(transactionSearchDocument.getPayAmount())
                .status(transactionSearchDocument.getStatus().getState())
                .receivedAt(transactionSearchDocument.getReceivedAt())
                .startedAt(transactionSearchDocument.getStartedAt())
                .endedAt(transactionSearchDocument.getEndedAt())
                .build();
    }
}
