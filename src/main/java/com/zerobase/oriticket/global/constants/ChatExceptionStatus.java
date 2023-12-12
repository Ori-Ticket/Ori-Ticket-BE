package com.zerobase.oriticket.global.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ChatExceptionStatus {

    CHAT_ROOM_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "채팅 방을 찾을 수 없습니다.");

    private int code;
    private String message;
}
