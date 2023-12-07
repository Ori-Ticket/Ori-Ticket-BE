package com.zerobase.oriticket.global.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TransactionExceptionStatus {

    TRANSACTION_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "거래를 찾을 수 없습니다."),
    CANNOT_MODIFY_STATE_OF_COMPLETED(HttpStatus.BAD_REQUEST.value(), "완료된 거래는 상태를 변경할 수 없습니다."),
    CANNOT_MODIFY_STATE_OF_CANCELED(HttpStatus.BAD_REQUEST.value(), "취소된 거래는 상태를 변경할 수 없습니다.");

    private int code;
    private String message;
}