package com.zerobase.oriticket.elasticsearch.transaction.entity;

import com.zerobase.oriticket.transaction.constants.TransactionStatus;
import com.zerobase.oriticket.transaction.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "transaction")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class TransactionSearchDocument {

    @Id
    private Long transactionId;

    private Long salePostId;

    private String memberName;

    private Integer payAmount;

    private TransactionStatus status;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime receivedAt;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime startedAt;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime endedAt;

    public static TransactionSearchDocument fromEntity(Transaction transaction){
        return TransactionSearchDocument.builder()
                .transactionId(transaction.getTransactionId())
                .salePostId(transaction.getSalePostId())
                .memberName("buyer")
//                .memberName(transaction.getMember().getUserName())
                .payAmount(transaction.getPayAmount())
                .status(transaction.getStatus())
                .receivedAt(transaction.getReceivedAt())
                .startedAt(transaction.getStartedAt())
                .endedAt(transaction.getEndedAt())
                .build();
    }
}
