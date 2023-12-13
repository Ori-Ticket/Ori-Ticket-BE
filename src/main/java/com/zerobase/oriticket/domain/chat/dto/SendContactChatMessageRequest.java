package com.zerobase.oriticket.domain.chat.dto;

import com.zerobase.oriticket.domain.chat.constants.ContactSenderType;
import com.zerobase.oriticket.domain.chat.entity.ContactChatMessage;
import com.zerobase.oriticket.domain.chat.entity.ContactChatRoom;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SendContactChatMessageRequest {

    private String senderType;
    private String message;

    public ContactChatMessage toEntity(ContactChatRoom contactChatRoom){
        return ContactChatMessage.builder()
                .contactChatRoom(contactChatRoom)
                .senderType(ContactSenderType.valueOf(senderType.toUpperCase()))
                .message(this.message)
                .chattedAt(LocalDateTime.now())
                .build();
    }
}
