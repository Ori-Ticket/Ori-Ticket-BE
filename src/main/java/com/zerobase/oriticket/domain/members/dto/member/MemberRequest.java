package com.zerobase.oriticket.domain.members.dto.member;

import com.zerobase.oriticket.domain.members.entity.Member;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequest {

    private String email;
    private String name;
    private String nickname;
    private LocalDateTime birthDate;
    private String phoneNum;

    public  Member toEntity() {
        return Member.builder()
                .email(this.email)
                .name(this.name)
                .nickname(this.nickname)
                .birthDate(this.birthDate)
                .phoneNum(this.phoneNum)
                .registeredAt(LocalDateTime.now())
                .build();
    }
}
