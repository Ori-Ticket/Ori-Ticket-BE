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

    @InjectMocks
    private ChatMessageService chatMessageService;

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @Mock
    private ChatMessageRepository chatMessageRepository;

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
        ChatMessage chatMessage = chatMessageService.register(CHAT_ROOM_ID, sendRequest);

        //then
        assertThat(chatMessage.getChatMessageId()).isEqualTo(4L);
        assertThat(chatMessage.getChatRoom()).isEqualTo(chatRoom);
        assertThat(chatMessage.getMemberId()).isEqualTo(5L);
        assertThat(chatMessage.getMessage()).isEqualTo("message");
        assertNotNull(chatMessage.getChattedAt());
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


        List<ChatMessage> chatMessages = Arrays.asList(chatMessage1, chatMessage2);

        given(chatMessageRepository.findAllByChatRoom_ChatRoomId(anyLong()))
                .willReturn(chatMessages);


        //when
        List<ChatMessage> chatMessageList = chatMessageService.getByRoom(1L);

        //then
        assertThat(chatMessageList).hasSize(2);
        assertThat(chatMessageList.get(0).getChatMessageId()).isEqualTo(1L);
        assertThat(chatMessageList.get(0).getChatRoom()).isEqualTo(chatRoom);
        assertThat(chatMessageList.get(0).getMemberId()).isEqualTo(40L);
        assertThat(chatMessageList.get(0).getMessage()).isEqualTo("message1");
        assertNotNull(chatMessageList.get(0).getChattedAt());
        assertThat(chatMessageList.get(1).getChatMessageId()).isEqualTo(2L);
        assertThat(chatMessageList.get(1).getChatRoom()).isEqualTo(chatRoom);
        assertThat(chatMessageList.get(1).getMemberId()).isEqualTo(50L);
        assertThat(chatMessageList.get(1).getMessage()).isEqualTo("message2");
        assertNotNull(chatMessageList.get(1).getChattedAt());
    }
}
