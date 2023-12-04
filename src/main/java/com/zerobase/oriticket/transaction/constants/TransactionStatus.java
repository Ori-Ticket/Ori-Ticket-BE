package com.zerobase.oriticket.transaction.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionStatus {

    PENDING("Pending", "결제 대기 상태"),
    RECEIVED("Received", "결제 완료 상태"),
    COMPLETED("Completed", "거래 완료 상태"),
    CANCELED("Canceled", "거래 취소 상태"),
    REPORTED("Reported", "거래 신고 상태");

    private String status;
    private String description;
}
