package com.zerobase.oriticket.chat.service;

import com.zerobase.oriticket.domain.chat.entity.ChatRoom;
import com.zerobase.oriticket.domain.chat.repository.ChatRoomRepository;
import com.zerobase.oriticket.domain.chat.service.ChatRoomService;
import com.zerobase.oriticket.domain.transaction.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ChatRoomServiceTest {

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @InjectMocks
    private ChatRoomService chatRoomService;

    private Transaction createTransaction(Long transactionId){
        return Transaction.builder()
                .transactionId(transactionId)
                .build();
    }

    @Test
    void successGet(){
        //given
        Transaction transaction = createTransaction(10L);

        Set<Long> members = new HashSet<>(Arrays.asList(1L, 2L));

        given(chatRoomRepository.findById(anyLong()))
                .willReturn(Optional.of(ChatRoom.builder()
                        .chatRoomId(5L)
                        .transaction(transaction)
                        .members(members)
                        .createdAt(LocalDateTime.now())
                        .build()));

        //when
        ChatRoom fetchedChatRoom = chatRoomService.get(5L);

        //then
        assertThat(fetchedChatRoom.getChatRoomId()).isEqualTo(5L);
        assertThat(fetchedChatRoom.getTransaction()).isEqualTo(transaction);
        assertThat(fetchedChatRoom.getMembers()).hasSize(2);
        assertThat(fetchedChatRoom.getMembers()).isEqualTo(members);
        assertNotNull(fetchedChatRoom.getCreatedAt());
    }

    @Test
    void successGetAll(){
        //given
        Set<Long> members = new HashSet<>(Arrays.asList(1L, 2L));
        Transaction transaction1 = createTransaction(10L);
        Transaction transaction2 = createTransaction(11L);

        ChatRoom chatRoom1 = ChatRoom.builder()
                .chatRoomId(5L)
                .transaction(transaction1)
                .members(members)
                .createdAt(LocalDateTime.now())
                .build();

        ChatRoom chatRoom2 = ChatRoom.builder()
                .chatRoomId(6L)
                .transaction(transaction2)
                .members(members)
                .createdAt(LocalDateTime.now())
                .build();

        List<ChatRoom> chatRoomList = Arrays.asList(chatRoom1, chatRoom2);

        given(chatRoomRepository.findAll(any(Pageable.class)))
                .willReturn(new PageImpl<>(chatRoomList));

        //when
        Page<ChatRoom> fetchedChatRooms = chatRoomService.getAll(1, 10);

        //then
        assertThat(fetchedChatRooms.getContent()).hasSize(2);
        assertThat(fetchedChatRooms.getContent().get(0).getChatRoomId()).isEqualTo(5L);
        assertThat(fetchedChatRooms.getContent().get(0).getTransaction()).isEqualTo(transaction1);
        assertThat(fetchedChatRooms.getContent().get(0).getMembers()).isEqualTo(members);
        assertNotNull(fetchedChatRooms.getContent().get(0).getCreatedAt());
        assertThat(fetchedChatRooms.getContent().get(1).getChatRoomId()).isEqualTo(6L);
        assertThat(fetchedChatRooms.getContent().get(1).getTransaction()).isEqualTo(transaction2);
        assertThat(fetchedChatRooms.getContent().get(1).getMembers()).isEqualTo(members);
        assertNotNull(fetchedChatRooms.getContent().get(1).getCreatedAt());

    }

    @Test
    void successGetByTransaction(){
        //given
        Set<Long> members = new HashSet<>(Arrays.asList(1L, 2L));
        Transaction transaction = createTransaction(10L);

        given(chatRoomRepository.findByTransaction_TransactionId(anyLong()))
                .willReturn(Optional.of(ChatRoom.builder()
                        .chatRoomId(5L)
                        .transaction(transaction)
                        .members(members)
                        .createdAt(LocalDateTime.now())
                        .build()));

        //when
        ChatRoom fetchedChatRoom = chatRoomService.getByTransaction(10L);

        //then
        assertThat(fetchedChatRoom.getChatRoomId()).isEqualTo(5L);
        assertThat(fetchedChatRoom.getTransaction()).isEqualTo(transaction);
        assertThat(fetchedChatRoom.getMembers()).isEqualTo(members);
        assertNotNull(fetchedChatRoom.getCreatedAt());

    }

    @Test
    void successGetByMember(){
        //given
        Set<Long> members = new HashSet<>(Arrays.asList(1L, 2L));
        Transaction transaction1 = createTransaction(10L);
        Transaction transaction2 = createTransaction(11L);

        ChatRoom chatRoom1 = ChatRoom.builder()
                .chatRoomId(5L)
                .transaction(transaction1)
                .members(members)
                .createdAt(LocalDateTime.now())
                .build();

        ChatRoom chatRoom2 = ChatRoom.builder()
                .chatRoomId(10L)
                .transaction(transaction2)
                .members(members)
                .createdAt(LocalDateTime.now())
                .build();

        List<ChatRoom> chatRoomList = Arrays.asList(chatRoom1, chatRoom2);

        given(chatRoomRepository.findAllByMembers(anyLong()))
                .willReturn(chatRoomList);

        //when
        List<ChatRoom> fetchedChatRooms = chatRoomService.getByMember(1L);

        //then
        assertThat(fetchedChatRooms).hasSize(2);
        assertThat(fetchedChatRooms.get(0).getChatRoomId()).isEqualTo(5L);
        assertThat(fetchedChatRooms.get(0).getTransaction()).isEqualTo(transaction1);
        assertThat(fetchedChatRooms.get(0).getMembers()).isEqualTo(members);
        assertNotNull(fetchedChatRooms.get(0).getCreatedAt());
        assertThat(fetchedChatRooms.get(1).getChatRoomId()).isEqualTo(10L);
        assertThat(fetchedChatRooms.get(1).getTransaction()).isEqualTo(transaction2);
        assertThat(fetchedChatRooms.get(1).getMembers()).isEqualTo(members);
        assertNotNull(fetchedChatRooms.get(1).getCreatedAt());

    }
}
