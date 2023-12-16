package com.zerobase.oriticket.domain.chat.dto;

import com.zerobase.oriticket.domain.chat.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;


@Getter
@Builder
public class ChatRoomResponse {

    private Long chatRoomId;
    private Long transactionId;
    private List<Long> members;
    private LocalDateTime createdAt;
    private LocalDateTime endedAt;

    public static ChatRoomResponse fromEntity(ChatRoom chatRoom){

        List<Long> membersList = new ArrayList<>(chatRoom.getMembers());
        Collections.sort(membersList);

        return ChatRoomResponse.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .transactionId(chatRoom.getTransaction().getTransactionId())
                .members(membersList)
                .createdAt(chatRoom.getCreatedAt())
                .endedAt(chatRoom.getEndedAt())
                .build();
    }
}
