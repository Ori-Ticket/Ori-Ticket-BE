package com.zerobase.oriticket.chat.service;

import com.zerobase.oriticket.domain.chat.dto.SendChatMessageRequest;
import com.zerobase.oriticket.domain.chat.entity.ChatMessage;
import com.zerobase.oriticket.domain.chat.entity.ChatRoom;
import com.zerobase.oriticket.domain.chat.repository.ChatMessageRepository;
import com.zerobase.oriticket.domain.chat.repository.ChatRoomRepository;
import com.zerobase.oriticket.domain.chat.service.ChatMessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ChatMessageServiceTest {

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @InjectMocks
    private ChatMessageService chatMessageService;

    private final static Long MEMBER_ID = 5L;
    private final static Long CHAT_ROOM_ID = 10L;
    private final static Long CHAT_MESSAGE_ID = 4L;
    private final static String MESSAGE = "message";

    @Test
    @Transactional
    void successRegister(){
        //given
        SendChatMessageRequest sendRequest =
                SendChatMessageRequest.builder()
                        .memberId(MEMBER_ID)
                        .message(MESSAGE)
                        .build();

        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomId(CHAT_ROOM_ID)
                .build();

        given(chatRoomRepository.findById(anyLong()))
                .willReturn(Optional.of(chatRoom));
        given(chatMessageRepository.save(any(ChatMessage.class)))
                .willReturn(ChatMessage.builder()
                        .chatMessageId(CHAT_MESSAGE_ID)
                        .chatRoom(chatRoom)
                        .memberId(MEMBER_ID)
                        .message(MESSAGE)
                        .chattedAt(LocalDateTime.now())
                        .build());

        //when
        ChatMessage fetchedChatMessage = chatMessageService.register(CHAT_ROOM_ID, sendRequest);

        //then
        assertThat(fetchedChatMessage.getChatMessageId()).isEqualTo(4L);
        assertThat(fetchedChatMessage.getChatRoom()).isEqualTo(chatRoom);
        assertThat(fetchedChatMessage.getMemberId()).isEqualTo(5L);
        assertThat(fetchedChatMessage.getMessage()).isEqualTo("message");
        assertNotNull(fetchedChatMessage.getChattedAt());
    }

    @Test
    void successGetByRoom(){
        //given
        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomId(1L)
                .build();

        ChatMessage chatMessage1 = ChatMessage.builder()
                .chatMessageId(1L)
                .chatRoom(chatRoom)
                .memberId(40L)
                .message("message1")
                .chattedAt(LocalDateTime.now())
                .build();

        ChatMessage chatMessage2 = ChatMessage.builder()
                .chatMessageId(2L)
                .chatRoom(chatRoom)
                .memberId(50L)
                .message("message2")
                .chattedAt(LocalDateTime.now())
                .build();


        List<ChatMessage> chatMessageList = Arrays.asList(chatMessage1, chatMessage2);

        given(chatMessageRepository.findAllByChatRoom_ChatRoomId(anyLong()))
                .willReturn(chatMessageList);


        //when
        List<ChatMessage> fetchedChatMessages = chatMessageService.getByRoom(1L);

        //then
        assertThat(fetchedChatMessages).hasSize(2);
        assertThat(fetchedChatMessages.get(0).getChatMessageId()).isEqualTo(1L);
        assertThat(fetchedChatMessages.get(0).getChatRoom()).isEqualTo(chatRoom);
        assertThat(fetchedChatMessages.get(0).getMemberId()).isEqualTo(40L);
        assertThat(fetchedChatMessages.get(0).getMessage()).isEqualTo("message1");
        assertNotNull(fetchedChatMessages.get(0).getChattedAt());
        assertThat(fetchedChatMessages.get(1).getChatMessageId()).isEqualTo(2L);
        assertThat(fetchedChatMessages.get(1).getChatRoom()).isEqualTo(chatRoom);
        assertThat(fetchedChatMessages.get(1).getMemberId()).isEqualTo(50L);
        assertThat(fetchedChatMessages.get(1).getMessage()).isEqualTo("message2");
        assertNotNull(fetchedChatMessages.get(1).getChattedAt());
    }
}
