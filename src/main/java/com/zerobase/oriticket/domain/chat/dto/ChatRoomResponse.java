package com.zerobase.oriticket.domain.chat.dto;

import com.zerobase.oriticket.domain.chat.entity.ChatRoom;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class ChatRoomResponse {

    private Long chatRoomId;
    private Long transactionId;
    private LocalDateTime createdAt;
    private LocalDateTime endedAt;

    public static ChatRoomResponse fromEntity(ChatRoom chatRoom){
        return ChatRoomResponse.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .transactionId(chatRoom.getTransaction().getTransactionId())
                .createdAt(chatRoom.getCreatedAt())
                .endedAt(chatRoom.getEndedAt())
                .build();
    }
}
