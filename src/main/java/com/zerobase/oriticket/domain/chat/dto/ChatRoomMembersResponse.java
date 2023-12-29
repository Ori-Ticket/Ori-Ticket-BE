package com.zerobase.oriticket.domain.chat.dto;

import com.zerobase.oriticket.domain.members.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRoomMembersResponse {
    private Long membersId;
    private String nickName;

    public static ChatRoomMembersResponse fromEntity(Member member){
        return ChatRoomMembersResponse.builder()
                .membersId(member.getMembersId())
                .nickName(member.getNickname())
                .build();
    }
}
