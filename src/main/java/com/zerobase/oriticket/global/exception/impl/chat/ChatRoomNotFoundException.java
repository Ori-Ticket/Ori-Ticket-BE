package com.zerobase.oriticket.global.exception.impl.chat;

import com.zerobase.oriticket.global.constants.ChatExceptionStatus;
import com.zerobase.oriticket.global.exception.AbstractException;

public class ChatRoomNotFoundException extends AbstractException {
    @Override
    public int getErrorCode() {
        return ChatExceptionStatus.CHAT_ROOM_NOT_FOUND.getCode();
    }

    @Override
    public String getMessage() {
        return ChatExceptionStatus.CHAT_ROOM_NOT_FOUND.getMessage();
    }
}
