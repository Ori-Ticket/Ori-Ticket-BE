package com.zerobase.oriticket.domain.chat.dto;

import com.zerobase.oriticket.domain.chat.entity.ChatMessage;
import com.zerobase.oriticket.domain.chat.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendChatMessageRequest {

    private Long memberId;
    private String message;

    public ChatMessage toEntity(ChatRoom chatRoom){
        return ChatMessage.builder()
                .chatRoom(chatRoom)
                .memberId(memberId)
                .message(this.message)
                .chattedAt(LocalDateTime.now())
                .build();
    }
}
