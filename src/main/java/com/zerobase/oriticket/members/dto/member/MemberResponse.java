package com.zerobase.oriticket.members.dto.member;

import com.zerobase.global.constants.MemberStatus;
import com.zerobase.oriticket.members.entity.Member;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public static Member fromEntity(com.zerobase.oriticket.members.entity.Member member) {
        return Member.builder()
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
