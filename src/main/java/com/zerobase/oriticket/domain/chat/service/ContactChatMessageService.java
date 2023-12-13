package com.zerobase.oriticket.domain.chat.service;

import com.zerobase.oriticket.domain.chat.dto.SendChatMessageRequest;
import com.zerobase.oriticket.domain.chat.dto.SendContactChatMessageRequest;
import com.zerobase.oriticket.domain.chat.entity.ContactChatMessage;
import com.zerobase.oriticket.domain.chat.entity.ContactChatRoom;
import com.zerobase.oriticket.domain.chat.repository.ContactChatMessageRepository;
import com.zerobase.oriticket.domain.chat.repository.ContactChatRoomRepository;
import com.zerobase.oriticket.global.exception.impl.chat.ChatRoomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactChatMessageService {

    private final ContactChatRoomRepository contactChatRoomRepository;
    private final ContactChatMessageRepository contactChatMessageRepository;

    public ContactChatMessage register(Long contactChatRoomId, SendContactChatMessageRequest request){

        ContactChatRoom contactChatRoom = contactChatRoomRepository.findById(contactChatRoomId)
                .orElseThrow(ChatRoomNotFoundException::new);

        return contactChatMessageRepository.save(request.toEntity(contactChatRoom));
    }

    public List<ContactChatMessage> getByRoom(Long contactChatRoomId){
        ContactChatRoom contactChatRoom =
                contactChatRoomRepository.findById(contactChatRoomId)
                        .orElseThrow(ChatRoomNotFoundException::new);

        return contactChatMessageRepository.findByContactChatRoom(contactChatRoom);
    }
}
