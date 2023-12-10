package com.zerobase.oriticket.domain.transaction.entity;

import com.zerobase.oriticket.domain.post.entity.Post;
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

    @OneToOne
    private Post salePost;

//    @ManyToOne
    private Long memberId;

    private Integer payAmount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private LocalDateTime receivedAt;

    @CreatedDate
    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    public void updateStatusToReceived(Integer payAmount){
        this.payAmount = payAmount;
        this.status = TransactionStatus.RECEIVED;
        this.receivedAt = LocalDateTime.now();
    }

    public void updateStatusToCompleted(){
        this.status = TransactionStatus.COMPLETED;
        this.endedAt = LocalDateTime.now();
    }

    public void updateStatusToCanceled(){
        this.status = TransactionStatus.CANCELED;
        this.endedAt = LocalDateTime.now();
    }

    public void updateStatusToReported(){
        this.status = TransactionStatus.REPORTED;
        this.endedAt = LocalDateTime.now();
    }
}
