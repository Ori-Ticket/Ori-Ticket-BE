package com.zerobase.oriticket.domain.chat.dto;

import com.zerobase.oriticket.domain.chat.entity.ChatRoom;
import com.zerobase.oriticket.domain.transaction.entity.Transaction;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterChatRoomRequest {
    private Long transactionId;

    public ChatRoom toEntity(Transaction transaction){
        return ChatRoom.builder()
                .transaction(transaction)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
