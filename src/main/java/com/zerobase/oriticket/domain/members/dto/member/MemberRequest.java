package com.zerobase.oriticket.domain.members.dto.member;

import com.zerobase.oriticket.domain.members.entity.Member;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequest {

    private String email;
    private String name;
    private String nickname;
    private LocalDateTime birthDate;
    private String phoneNum;
    private LocalDateTime registeredAt;
    private LocalDateTime modifiedAt;

    public static Member fromEntity(Member member) {
        return Member.builder()
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .birthDate(member.getBirthDate())
                .phoneNum(member.getPhoneNum())
                .registeredAt(member.getRegisteredAt())
                .modifiedAt(member.getModifiedAt())
                .build();
    }
}
