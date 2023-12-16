package com.zerobase.oriticket.domain.chat.repository;

import com.zerobase.oriticket.domain.chat.entity.ContactChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactChatRoomRepository extends JpaRepository<ContactChatRoom, Long> {
    List<ContactChatRoom> findAllByMemberId(Long memberId);
}
