package com.zerobase.oriticket.domain.members.dto.member;

import com.zerobase.oriticket.domain.members.constants.MemberStatus;
import com.zerobase.oriticket.domain.members.entity.Member;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

    public MemberResponse fromEntity() {
        return MemberResponse.builder()
                .email(this.email)
                .name(this.name)
                .nickname(this.nickname)
                .birthDate(this.birthDate)
                .phoneNum(this.phoneNum)
                .status(this.status)
                .registeredAt(this.registeredAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }
}
