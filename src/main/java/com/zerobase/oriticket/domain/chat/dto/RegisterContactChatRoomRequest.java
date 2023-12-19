package com.zerobase.oriticket.domain.chat.dto;

import com.zerobase.oriticket.domain.chat.entity.ContactChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterContactChatRoomRequest {

    //멤버 연동 예정
    private Long memberId;
    
    public ContactChatRoom toEntity(){
        return ContactChatRoom.builder()
                .memberId(this.memberId)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
