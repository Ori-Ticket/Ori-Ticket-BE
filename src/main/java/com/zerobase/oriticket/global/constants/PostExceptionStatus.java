package com.zerobase.oriticket.global.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostExceptionStatus {

    SALE_POST_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "판매 글 정보를 찾을 수 없습니다");

    private int code;
    private String message;
}
