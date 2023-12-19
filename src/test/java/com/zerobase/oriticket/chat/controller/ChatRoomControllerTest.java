package com.zerobase.oriticket.chat.controller;

import com.zerobase.oriticket.domain.chat.controller.ChatRoomController;
import com.zerobase.oriticket.domain.chat.entity.ChatRoom;
import com.zerobase.oriticket.domain.chat.service.ChatRoomService;
import com.zerobase.oriticket.domain.transaction.entity.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChatRoomController.class)
public class ChatRoomControllerTest {

    @MockBean
    private ChatRoomService chatRoomService;

    @Autowired
    private MockMvc mockMvc;

    private final static String BASE_URL = "/chatroom";

    @Test
    @DisplayName("ChatRoom 조회 성공")
    void successGet() throws Exception {
        //given
        Transaction transaction = Transaction.builder()
                .transactionId(1L)
                .build();

        Set<Long> members = Set.of(1L, 2L);

        given(chatRoomService.get(anyLong()))
                .willReturn(ChatRoom.builder()
                        .chatRoomId(1L)
                        .transaction(transaction)
                        .members(members)
                        .createdAt(LocalDateTime.now())
                        .build());

        //when
        //then
        mockMvc.perform(get(BASE_URL+"?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatRoomId").value(1L))
                .andExpect(jsonPath("$.transactionId").value(1L))
                .andExpect(jsonPath("$.members[0]").value(1L))
                .andExpect(jsonPath("$.members[1]").value(2L))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    @DisplayName("모든 ChatRoom 조회 성공")
    void successGetAll() throws Exception {
        //given
        Transaction transaction1 = Transaction.builder()
                .transactionId(1L)
                .build();

        Transaction transaction2 = Transaction.builder()
                .transactionId(2L)
                .build();

        Set<Long> members1 = Set.of(1L, 2L);
        Set<Long> members2 = Set.of(3L, 4L);

        ChatRoom chatRoom1 = ChatRoom.builder()
                .chatRoomId(1L)
                .transaction(transaction1)
                .members(members1)
                .createdAt(LocalDateTime.now())
                .build();

        ChatRoom chatRoom2 = ChatRoom.builder()
                .chatRoomId(2L)
                .transaction(transaction2)
                .members(members2)
                .createdAt(LocalDateTime.now())
                .build();

        List<ChatRoom> chatRoomList = Arrays.asList(chatRoom1, chatRoom2);

        Page<ChatRoom> chatRooms =
                new PageImpl<>(chatRoomList);

        given(chatRoomService.getAll(anyInt(), anyInt()))
                .willReturn(chatRooms);

        //when
        //then
        mockMvc.perform(get(BASE_URL+"/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].chatRoomId").value(1L))
                .andExpect(jsonPath("$.content[0].transactionId").value(1L))
                .andExpect(jsonPath("$.content[0].members[0]").value(1L))
                .andExpect(jsonPath("$.content[0].members[1]").value(2L))
                .andExpect(jsonPath("$.content[0].createdAt").exists())
                .andExpect(jsonPath("$.content[1].chatRoomId").value(2L))
                .andExpect(jsonPath("$.content[1].transactionId").value(2L))
                .andExpect(jsonPath("$.content[1].members[0]").value(3L))
                .andExpect(jsonPath("$.content[1].members[1]").value(4L))
                .andExpect(jsonPath("$.content[1].createdAt").exists());
    }

    @Test
    @DisplayName("Transaction 으로 ChatRoom 조회 성공")
    void successGetByTransaction() throws Exception {
        //given
        Transaction transaction = Transaction.builder()
                .transactionId(1L)
                .build();

        Set<Long> members = Set.of(1L, 2L);

        given(chatRoomService.getByTransaction(anyLong()))
                .willReturn(ChatRoom.builder()
                        .chatRoomId(1L)
                        .transaction(transaction)
                        .members(members)
                        .createdAt(LocalDateTime.now())
                        .build());

        //when
        //then
        mockMvc.perform(get(BASE_URL+"/transaction?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatRoomId").value(1L))
                .andExpect(jsonPath("$.transactionId").value(1L))
                .andExpect(jsonPath("$.members[0]").value(1L))
                .andExpect(jsonPath("$.members[1]").value(2L))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    @DisplayName("Member 로 ChatRoom 조회 성공")
    void successGetByMember() throws Exception {
        //given
        Transaction transaction1 = Transaction.builder()
                .transactionId(1L)
                .build();

        Transaction transaction2 = Transaction.builder()
                .transactionId(2L)
                .build();

        Set<Long> members1 = Set.of(1L, 2L);
        Set<Long> members2 = Set.of(1L, 3L);

        ChatRoom chatRoom1 = ChatRoom.builder()
                .chatRoomId(1L)
                .transaction(transaction1)
                .members(members1)
                .createdAt(LocalDateTime.now())
                .build();

        ChatRoom chatRoom2 = ChatRoom.builder()
                .chatRoomId(2L)
                .transaction(transaction2)
                .members(members2)
                .createdAt(LocalDateTime.now())
                .build();

        List<ChatRoom> chatRooms = Arrays.asList(chatRoom1, chatRoom2);

        given(chatRoomService.getByMember(anyLong()))
                .willReturn(chatRooms);

        //when
        //then
        mockMvc.perform(get(BASE_URL+"/member?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].chatRoomId").value(1L))
                .andExpect(jsonPath("$.[0].transactionId").value(1L))
                .andExpect(jsonPath("$.[0].members[0]").value(1L))
                .andExpect(jsonPath("$.[0].members[1]").value(2L))
                .andExpect(jsonPath("$.[0].createdAt").exists())
                .andExpect(jsonPath("$.[1].chatRoomId").value(2L))
                .andExpect(jsonPath("$.[1].transactionId").value(2L))
                .andExpect(jsonPath("$.[1].members[0]").value(1L))
                .andExpect(jsonPath("$.[1].members[1]").value(3L))
                .andExpect(jsonPath("$.[1].createdAt").exists());
    }
}