package com.zerobase.oriticket.domain.chat.controller;

import com.zerobase.oriticket.domain.chat.dto.ChatRoomResponse;
import com.zerobase.oriticket.domain.chat.dto.RegisterChatRoomRequest;
import com.zerobase.oriticket.domain.chat.entity.ChatRoom;
import com.zerobase.oriticket.domain.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping
    public ChatRoomResponse register(
            @RequestBody RegisterChatRoomRequest request
    ){
        ChatRoom chatRoom = chatRoomService.register(request);

        return ChatRoomResponse.fromEntity(chatRoom);
    }
}
