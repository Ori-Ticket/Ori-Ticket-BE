package com.zerobase.oriticket.domain.transaction.entity;

import com.zerobase.oriticket.domain.transaction.constants.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

//    @OneToOne
    private Long salePostId;

//    @ManyToOne
    private Long memberId;

    private Integer payAmount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private LocalDateTime receivedAt;

    @CreatedDate
    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    public void updateStatus(TransactionStatus transactionStatus){
        this.status = transactionStatus;
    }
}
