package com.zerobase.oriticket.domain.chat.controller;

import com.zerobase.oriticket.domain.chat.dto.RegisterChatMessageRequest;
import com.zerobase.oriticket.domain.chat.dto.ChatMessageResponse;
import com.zerobase.oriticket.domain.chat.entity.ChatMessage;
import com.zerobase.oriticket.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/{chatRoomId}")
    @SendTo("/send/{chatRoomId}")
    public ChatMessageResponse chat(
            @DestinationVariable("chatRoomId") Long chatRoomId,
            RegisterChatMessageRequest message
    ){
        ChatMessage chatMessage = chatService.registerMessage(chatRoomId, message);

        return ChatMessageResponse.fromEntity(chatMessage);
    }
}
