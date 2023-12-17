package com.zerobase.oriticket.domain.chat.repository;

import com.zerobase.oriticket.domain.chat.entity.ContactChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactChatRoomRepository extends JpaRepository<ContactChatRoom, Long> {
    Optional<ContactChatRoom> findByMemberId(Long memberId);
}
