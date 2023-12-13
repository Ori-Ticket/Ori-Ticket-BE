package com.zerobase.oriticket.domain.chat.service;

import com.zerobase.oriticket.domain.chat.dto.RegisterContactChatRoomRequest;
import com.zerobase.oriticket.domain.chat.entity.ContactChatRoom;
import com.zerobase.oriticket.domain.chat.repository.ContactChatRoomRepository;
import com.zerobase.oriticket.global.exception.impl.chat.ChatRoomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactChatRoomService {

    private final ContactChatRoomRepository contactChatRoomRepository;

    private static final String CREATED_AT = "createdAt";

    public ContactChatRoom register(RegisterContactChatRoomRequest request) {

        // 멤버 유효성 체크

        return contactChatRoomRepository.save(request.toEntity());
    }

    public ContactChatRoom get(Long contactChatRoomId) {
        ContactChatRoom contactChatRoom =
                contactChatRoomRepository.findById(contactChatRoomId)
                        .orElseThrow(ChatRoomNotFoundException::new);

        return contactChatRoom;
    }

    public Page<ContactChatRoom> getAll(int page, int size) {
        Sort sort = Sort.by(CREATED_AT).descending();

        Pageable pageable = PageRequest.of(page-1, size, sort);

        return contactChatRoomRepository.findAll(pageable);
    }

    public List<ContactChatRoom> getByMember(Long memberId){
        //멤버 유효성 체크

        return contactChatRoomRepository.findByMemberId(memberId);
    }
}
