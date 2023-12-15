package com.zerobase.oriticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.oriticket.domain.chat.controller.ContactChatRoomController;
import com.zerobase.oriticket.domain.chat.dto.RegisterContactChatRoomRequest;
import com.zerobase.oriticket.domain.chat.entity.ContactChatRoom;
import com.zerobase.oriticket.domain.chat.service.ContactChatRoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactChatRoomController.class)
public class ContactChatRoomControllerTest {

    @MockBean
    private ContactChatRoomService contactChatRoomService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final static String BASE_URL = "/contact";

    @Test
    @DisplayName("ContactChatRoom 등록 성공")
    void successRegister() throws Exception{
        //given
        RegisterContactChatRoomRequest contactChatRoomRequest =
                RegisterContactChatRoomRequest.builder()
                        .memberId(1L)
                        .build();

        given(contactChatRoomService.register(any()))
                .willReturn(ContactChatRoom.builder()
                        .contactChatRoomId(1L)
                        .memberId(1L)
                        .createdAt(LocalDateTime.now())
                        .build());

        //when
        //then
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactChatRoomRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactChatRoomId").value(1L))
                .andExpect(jsonPath("$.memberId").value(1L))
                .andExpect(jsonPath("$.createdAt").exists());

    }

    @Test
    @DisplayName("ContactChatRoom 조회 성공")
    void successGet() throws Exception{
        //given
        given(contactChatRoomService.get(anyLong()))
                .willReturn(ContactChatRoom.builder()
                        .contactChatRoomId(1L)
                        .memberId(1L)
                        .createdAt(LocalDateTime.now())
                        .build());

        //when
        //then
        mockMvc.perform(get(BASE_URL+"?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactChatRoomId").value(1L))
                .andExpect(jsonPath("$.memberId").value(1L))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    @DisplayName("모든 ContactChatRoom 조회 성공")
    void successGetAll() throws Exception{
        //given
        ContactChatRoom contactChatRoom1 = ContactChatRoom.builder()
                .contactChatRoomId(1L)
                .memberId(1L)
                .createdAt(LocalDateTime.now())
                .build();

        ContactChatRoom contactChatRoom2 = ContactChatRoom.builder()
                .contactChatRoomId(2L)
                .memberId(2L)
                .createdAt(LocalDateTime.now())
                .build();

        List<ContactChatRoom> contactChatRoomList = Arrays.asList(contactChatRoom1, contactChatRoom2);
        Page<ContactChatRoom> contactChatRooms = new PageImpl<>(contactChatRoomList);

        given(contactChatRoomService.getAll(anyInt(), anyInt()))
                .willReturn(contactChatRooms);

        //when
        //then
        mockMvc.perform(get(BASE_URL+"/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].contactChatRoomId").value(1L))
                .andExpect(jsonPath("$.content[0].memberId").value(1L))
                .andExpect(jsonPath("$.content[0]createdAt").exists())
                .andExpect(jsonPath("$.content[1].contactChatRoomId").value(2L))
                .andExpect(jsonPath("$.content[1].memberId").value(2L))
                .andExpect(jsonPath("$.content[1]createdAt").exists());
    }

    @Test
    @DisplayName("Member 로 ContactChatRoom 조회 성공")
    void successGetByMember() throws Exception{
        //given
        ContactChatRoom contactChatRoom1 = ContactChatRoom.builder()
                .contactChatRoomId(1L)
                .memberId(1L)
                .createdAt(LocalDateTime.now())
                .build();

        ContactChatRoom contactChatRoom2 = ContactChatRoom.builder()
                .contactChatRoomId(2L)
                .memberId(2L)
                .createdAt(LocalDateTime.now())
                .build();

        List<ContactChatRoom> contactChatRoomList = Arrays.asList(contactChatRoom1, contactChatRoom2);

        given(contactChatRoomService.getByMember(anyLong()))
                .willReturn(contactChatRoomList);

        //when
        //then
        mockMvc.perform(get(BASE_URL+"/member?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].contactChatRoomId").value(1L))
                .andExpect(jsonPath("$.[0].memberId").value(1L))
                .andExpect(jsonPath("$.[0]createdAt").exists())
                .andExpect(jsonPath("$.[1].contactChatRoomId").value(2L))
                .andExpect(jsonPath("$.[1].memberId").value(2L))
                .andExpect(jsonPath("$.[1]createdAt").exists());
    }
}
