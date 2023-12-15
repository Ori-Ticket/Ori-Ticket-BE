package com.zerobase.oriticket.controller;

import com.zerobase.oriticket.domain.chat.constants.ContactSenderType;
import com.zerobase.oriticket.domain.chat.controller.ChatController;
import com.zerobase.oriticket.domain.chat.dto.ChatMessageResponse;
import com.zerobase.oriticket.domain.chat.dto.ContactChatMessageResponse;
import com.zerobase.oriticket.domain.chat.dto.SendChatMessageRequest;
import com.zerobase.oriticket.domain.chat.dto.SendContactChatMessageRequest;
import com.zerobase.oriticket.domain.chat.entity.ChatMessage;
import com.zerobase.oriticket.domain.chat.entity.ChatRoom;
import com.zerobase.oriticket.domain.chat.entity.ContactChatMessage;
import com.zerobase.oriticket.domain.chat.entity.ContactChatRoom;
import com.zerobase.oriticket.domain.chat.service.ChatMessageService;
import com.zerobase.oriticket.domain.chat.service.ContactChatMessageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ChatControllerTest {

    @InjectMocks
    private ChatController chatController;

    @Mock
    private ChatMessageService chatMessageService;

    @Mock
    private ContactChatMessageService contactChatMessageService;

    @Test
    @DisplayName("1:1 채팅 보내기 성공")
    void successChat(){
        //given
        SendChatMessageRequest messageRequest =
                SendChatMessageRequest.builder()
                        .memberId(1L)
                        .message("message")
                        .build();

        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomId(1L)
                .build();

        ChatMessage chatMessage =
                ChatMessage.builder()
                        .chatMessageId(4L)
                        .chatRoom(chatRoom)
                        .memberId(50L)
                        .message("message")
                        .chattedAt(LocalDateTime.now())
                        .build();

        given(chatMessageService.register(anyLong(), any()))
                .willReturn(chatMessage);

        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<SendChatMessageRequest> requestCaptor = ArgumentCaptor.forClass(SendChatMessageRequest.class);
        //when
        ChatMessageResponse chatMessageResponse = chatController.chat(1L, messageRequest);

        //then
        verify(chatMessageService, times(1)).register(captor.capture(), requestCaptor.capture());
        assertEquals(1L, captor.getValue());
        assertEquals(messageRequest, requestCaptor.getValue());
        assertEquals(4L, chatMessageResponse.getChatMessageId());
        assertEquals(1L, chatMessageResponse.getChatRoomId());
        assertEquals(50L, chatMessageResponse.getMemberId());
        assertEquals("message", chatMessageResponse.getMessage());
        assertNotNull(chatMessageResponse.getChattedAt());
    }

    @Test
    @DisplayName("1:1 문의 채팅 보내기 성공")
    void successContact(){
        //given
        SendContactChatMessageRequest messageRequest =
                SendContactChatMessageRequest.builder()
                        .senderType("Admin")
                        .message("message")
                        .build();

        ContactChatRoom contactChatRoom = ContactChatRoom.builder()
                .contactChatRoomId(1L)
                .build();

        ContactChatMessage contactChatMessage =
                ContactChatMessage.builder()
                        .contactChatMessageId(2L)
                        .contactChatRoom(contactChatRoom)
                        .senderType(ContactSenderType.ADMIN)
                        .message("message")
                        .chattedAt(LocalDateTime.now())
                        .build();

        given(contactChatMessageService.register(anyLong(), any()))
                .willReturn(contactChatMessage);

        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<SendContactChatMessageRequest> requestCaptor =
                ArgumentCaptor.forClass(SendContactChatMessageRequest.class);
        //when
        ContactChatMessageResponse contactChatMessageResponse =
                chatController.contact(1L, messageRequest);

        //then
        verify(contactChatMessageService, times(1)).register(captor.capture(), requestCaptor.capture());
        assertEquals(1L, captor.getValue());
        assertEquals(messageRequest, requestCaptor.getValue());
        assertEquals(2L, contactChatMessageResponse.getContactChatMessageId());
        assertEquals(1L, contactChatMessageResponse.getContactChatRoomId());
        assertEquals(ContactSenderType.ADMIN, contactChatMessageResponse.getSenderType());
        assertEquals("message", contactChatMessageResponse.getMessage());
        assertNotNull(contactChatMessageResponse.getChattedAt());
    }
}
