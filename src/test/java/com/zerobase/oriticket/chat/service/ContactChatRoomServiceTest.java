package com.zerobase.oriticket.chat.service;

import com.zerobase.oriticket.domain.chat.dto.RegisterContactChatRoomRequest;
import com.zerobase.oriticket.domain.chat.entity.ContactChatRoom;
import com.zerobase.oriticket.domain.chat.repository.ContactChatRoomRepository;
import com.zerobase.oriticket.domain.chat.service.ContactChatRoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
public class ContactChatRoomServiceTest {

    @InjectMocks
    private ContactChatRoomService contactChatRoomService;

    @Mock
    private ContactChatRoomRepository contactChatRoomRepository;

    @Test
    @Transactional
    void successRegister(){
        //given
        RegisterContactChatRoomRequest registerRequest =
                RegisterContactChatRoomRequest.builder()
                        .memberId(10L)
                        .build();

        given(contactChatRoomRepository.save(any(ContactChatRoom.class)))
                .willReturn(ContactChatRoom.builder()
                        .contactChatRoomId(1L)
                        .memberId(10L)
                        .createdAt(LocalDateTime.now())
                        .build());

        //when
        ContactChatRoom contactChatRoom = contactChatRoomService.register(registerRequest);

        //then
        assertThat(contactChatRoom.getContactChatRoomId()).isEqualTo(1L);
        assertThat(contactChatRoom.getMemberId()).isEqualTo(10L);
        assertNotNull(contactChatRoom.getCreatedAt());

    }

    @Test
    void successGet(){
        //given
        RegisterContactChatRoomRequest registerRequest =
                RegisterContactChatRoomRequest.builder()
                        .memberId(10L)
                        .build();

        given(contactChatRoomRepository.findById(anyLong()))
                .willReturn(Optional.of(ContactChatRoom.builder()
                        .contactChatRoomId(1L)
                        .memberId(10L)
                        .createdAt(LocalDateTime.now())
                        .build()));

        //when
        ContactChatRoom contactChatRoom = contactChatRoomService.get(1L);

        //then
        assertThat(contactChatRoom.getContactChatRoomId()).isEqualTo(1L);
        assertThat(contactChatRoom.getMemberId()).isEqualTo(10L);
        assertNotNull(contactChatRoom.getCreatedAt());

    }

    @Test
    void successGetAll(){
        //given
        ContactChatRoom contactChatRoom1 = ContactChatRoom.builder()
                .contactChatRoomId(1L)
                .memberId(10L)
                .createdAt(LocalDateTime.now())
                .build();

        ContactChatRoom contactChatRoom2 = ContactChatRoom.builder()
                .contactChatRoomId(2L)
                .memberId(11L)
                .createdAt(LocalDateTime.now())
                .build();

        List<ContactChatRoom> contactChatRoomList = Arrays.asList(contactChatRoom1, contactChatRoom2);

        given(contactChatRoomRepository.findAll(any(Pageable.class)))
                .willReturn(new PageImpl<>(contactChatRoomList));

        //when
        Page<ContactChatRoom> contactChatRooms = contactChatRoomService.getAll(1, 10);

        //then
        assertThat(contactChatRooms.getContent()).hasSize(2);
        assertThat(contactChatRooms.getContent().get(0).getContactChatRoomId()).isEqualTo(1L);
        assertThat(contactChatRooms.getContent().get(0).getMemberId()).isEqualTo(10L);
        assertNotNull(contactChatRooms.getContent().get(0).getCreatedAt());
        assertThat(contactChatRooms.getContent().get(1).getContactChatRoomId()).isEqualTo(2L);
        assertThat(contactChatRooms.getContent().get(1).getMemberId()).isEqualTo(11L);
        assertNotNull(contactChatRooms.getContent().get(1).getCreatedAt());

    }

    @Test
    void successGetByMember(){
        //given
        given(contactChatRoomRepository.findByMemberId(anyLong()))
                .willReturn(Optional.of(ContactChatRoom.builder()
                        .contactChatRoomId(1L)
                        .memberId(10L)
                        .createdAt(LocalDateTime.now())
                        .build()));

        //when
        ContactChatRoom contactChatRoom = contactChatRoomService.getByMember(10L);

        //then
        assertThat(contactChatRoom.getContactChatRoomId()).isEqualTo(1L);
        assertThat(contactChatRoom.getMemberId()).isEqualTo(10L);
        assertNotNull(contactChatRoom.getCreatedAt());
    }
}
