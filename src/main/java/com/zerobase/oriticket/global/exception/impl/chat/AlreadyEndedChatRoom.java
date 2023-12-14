package com.zerobase.oriticket.global.exception.impl.chat;

import com.zerobase.oriticket.global.constants.ChatExceptionStatus;
import com.zerobase.oriticket.global.exception.AbstractException;


public class AlreadyEndedChatRoom extends AbstractException {
    @Override
    public int getErrorCode() {
        return ChatExceptionStatus.ALREADY_ENDED_CHAT_ROOM.getCode();
    }

    @Override
    public String getMessage() {
        return ChatExceptionStatus.ALREADY_ENDED_CHAT_ROOM.getMessage();
    }
}
