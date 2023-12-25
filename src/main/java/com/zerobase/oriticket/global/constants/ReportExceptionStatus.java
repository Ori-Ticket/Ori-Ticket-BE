package com.zerobase.oriticket.global.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReportExceptionStatus {

    REPORT_POST_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "판매 글 정보를 찾을 수 없습니다."),
    REPORT_TRANSACTION_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "판매 글 정보를 찾을 수 없습니다.");

    private final int code;
    private final String message;
}
