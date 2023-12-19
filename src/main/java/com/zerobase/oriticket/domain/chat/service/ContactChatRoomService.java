package com.zerobase.oriticket.domain.chat.service;

import com.zerobase.oriticket.domain.chat.dto.RegisterContactChatRoomRequest;
import com.zerobase.oriticket.domain.chat.entity.ContactChatRoom;
import com.zerobase.oriticket.domain.chat.repository.ContactChatRoomRepository;
import com.zerobase.oriticket.global.exception.impl.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.zerobase.oriticket.global.constants.ChatExceptionStatus.CHAT_ROOM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ContactChatRoomService {

    private final ContactChatRoomRepository contactChatRoomRepository;

    private static final String CREATED_AT = "createdAt";

    @Transactional
    public ContactChatRoom register(RegisterContactChatRoomRequest request) {

        // 멤버 유효성 체크

        return contactChatRoomRepository.save(request.toEntity());
    }

    @Transactional(readOnly = true)
    public ContactChatRoom get(Long contactChatRoomId) {

        return contactChatRoomRepository.findById(contactChatRoomId)
                .orElseThrow(() -> new CustomException(CHAT_ROOM_NOT_FOUND.getCode(), CHAT_ROOM_NOT_FOUND.getMessage()));
    }

    @Transactional(readOnly = true)
    public Page<ContactChatRoom> getAll(int page, int size) {
        Sort sort = Sort.by(CREATED_AT).descending();

        Pageable pageable = PageRequest.of(page-1, size, sort);

        return contactChatRoomRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public ContactChatRoom getByMember(Long memberId){
        //멤버 유효성 체크

        return contactChatRoomRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException(CHAT_ROOM_NOT_FOUND.getCode(), CHAT_ROOM_NOT_FOUND.getMessage()));
    }
}
