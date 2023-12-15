package com.zerobase.oriticket.domain.chat.service;

import com.zerobase.oriticket.domain.chat.entity.ChatRoom;
import com.zerobase.oriticket.domain.chat.repository.ChatRoomRepository;
import com.zerobase.oriticket.domain.transaction.entity.Transaction;
import com.zerobase.oriticket.domain.transaction.repository.TransactionRepository;
import com.zerobase.oriticket.global.exception.impl.chat.ChatRoomNotFoundException;
import com.zerobase.oriticket.global.exception.impl.transaction.TransactionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final TransactionRepository transactionRepository;

    private static final String CREATED_AT = "createdAt";

    public ChatRoom get(Long chatRoomId){
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(ChatRoomNotFoundException::new);

        return chatRoom;
    }

    public Page<ChatRoom> getAll(int page, int size) {

        Sort sort = Sort.by(CREATED_AT).descending();

        Pageable pageable = PageRequest.of(page-1, size, sort);

        return chatRoomRepository.findAll(pageable);
    }

    public ChatRoom getByTransaction(Long transactionId){
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(TransactionNotFoundException::new);

        ChatRoom chatRoom = chatRoomRepository.findByTransaction(transaction)
                .orElseThrow(ChatRoomNotFoundException::new);

        return chatRoom;
    }

    public List<ChatRoom> getByMember(Long memberId){

        // 멤버 가져오기

        List<ChatRoom> chatRoom = chatRoomRepository.findAllByMembers(memberId);

        return chatRoom;
    }
}
