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
import org.springframework.data.domain.PageRequest;
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

    @InjectMocks
    private ChatRoomService chatRoomService;

    @Mock
    private ChatRoomRepository chatRoomRepository;

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
        ChatRoom chatRoom = chatRoomService.get(5L);

        //then
        assertThat(chatRoom.getChatRoomId()).isEqualTo(5L);
        assertThat(chatRoom.getTransaction()).isEqualTo(transaction);
        assertThat(chatRoom.getMembers()).hasSize(2);
        assertThat(chatRoom.getMembers()).isEqualTo(members);
        assertNotNull(chatRoom.getCreatedAt());
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
        Page<ChatRoom> chatRooms = chatRoomService.getAll(1, 10);

        //then
        assertThat(chatRooms.getContent()).hasSize(2);
        assertThat(chatRooms.getContent().get(0).getChatRoomId()).isEqualTo(5L);
        assertThat(chatRooms.getContent().get(0).getTransaction()).isEqualTo(transaction1);
        assertThat(chatRooms.getContent().get(0).getMembers()).isEqualTo(members);
        assertNotNull(chatRooms.getContent().get(0).getCreatedAt());
        assertThat(chatRooms.getContent().get(1).getChatRoomId()).isEqualTo(6L);
        assertThat(chatRooms.getContent().get(1).getTransaction()).isEqualTo(transaction2);
        assertThat(chatRooms.getContent().get(1).getMembers()).isEqualTo(members);
        assertNotNull(chatRooms.getContent().get(1).getCreatedAt());

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
        ChatRoom chatRoom = chatRoomService.getByTransaction(10L);

        //then
        assertThat(chatRoom.getChatRoomId()).isEqualTo(5L);
        assertThat(chatRoom.getTransaction()).isEqualTo(transaction);
        assertThat(chatRoom.getMembers()).isEqualTo(members);
        assertNotNull(chatRoom.getCreatedAt());

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
        List<ChatRoom> chatRooms = chatRoomService.getByMember(1L);

        //then
        assertThat(chatRooms).hasSize(2);
        assertThat(chatRooms.get(0).getChatRoomId()).isEqualTo(5L);
        assertThat(chatRooms.get(0).getTransaction()).isEqualTo(transaction1);
        assertThat(chatRooms.get(0).getMembers()).isEqualTo(members);
        assertNotNull(chatRooms.get(0).getCreatedAt());
        assertThat(chatRooms.get(1).getChatRoomId()).isEqualTo(10L);
        assertThat(chatRooms.get(1).getTransaction()).isEqualTo(transaction2);
        assertThat(chatRooms.get(1).getMembers()).isEqualTo(members);
        assertNotNull(chatRooms.get(1).getCreatedAt());

    }
}
