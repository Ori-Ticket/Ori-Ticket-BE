package com.zerobase.oriticket.domain.chat.repository;

import com.zerobase.oriticket.domain.chat.entity.ChatMessage;
import com.zerobase.oriticket.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository  extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoom(ChatRoom chatRoom);
}
