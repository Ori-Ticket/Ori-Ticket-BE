package com.zerobase.oriticket.domain.members.dto.member;

import com.zerobase.oriticket.domain.members.entity.Member;
import com.zerobase.oriticket.global.constants.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponse {

    private String email;
    private String name;
    private String nickname;
    private LocalDateTime birthDate;
    private String phoneNum;
    private MemberStatus status;
    private LocalDateTime registeredAt;
    private LocalDateTime modifiedAt;

    public static MemberResponse fromEntity(Member member) {
        return MemberResponse.builder()
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .birthDate(member.getBirthDate())
                .phoneNum(member.getPhoneNum())
                .status(member.getStatus())
                .registeredAt(member.getRegisteredAt())
                .modifiedAt(member.getModifiedAt())
                .build();
    }
}
