package com.zerobase.oriticket.domain.chat.dto;

import com.zerobase.oriticket.domain.chat.entity.ChatMessage;
import com.zerobase.oriticket.domain.chat.entity.ChatRoom;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterChatMessageRequest {

    private String sender;
    private String message;

    public ChatMessage toEntity(ChatRoom chatRoom){
        return ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(this.sender)
                .message(this.message)
                .chattedAt(LocalDateTime.now())
                .build();
    }
}
