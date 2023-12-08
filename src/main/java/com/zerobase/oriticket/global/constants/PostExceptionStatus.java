package com.zerobase.oriticket.global.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostExceptionStatus {

    SALE_POST_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "판매 글 정보를 찾을 수 없습니다."),
    SPORTS_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "스포츠 정보를 찾을 수 없습니다."),
    STADIUM_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "경기장 정보를 찾을 수 없습니다."),
    AWAY_TEAM_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "팀 정보를 찾을 수 없습니다."),
    CANNOT_DELETE_POST_TRANSACTION_EXIST(HttpStatus.BAD_REQUEST.value(), "생성된 거래가 있습니다. 판매글 삭제를 할 수 없습니다.");

    private int code;
    private String message;
}
