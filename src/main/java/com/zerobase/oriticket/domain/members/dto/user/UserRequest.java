package com.zerobase.oriticket.domain.members.dto.user;

import com.zerobase.oriticket.domain.members.constants.MemberStatus;
import com.zerobase.oriticket.domain.members.constants.RoleType;
import com.zerobase.oriticket.domain.members.entity.Member;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserRequest {

    private long id;
    private String email;
    private String name;
    private String nickname;
    private LocalDateTime birthDate;
    private String phoneNum;
    private RoleType role;
    private MemberStatus status;
    private String oauth;
    private String password;

    public Member toEntityKakao() {
        return Member.builder()
                .email(this.email)
                .name(this.name)
                .nickname(this.nickname)
                .birthDate(this.birthDate)
                .phoneNum(this.phoneNum)
                .password(this.password)
                .role(this.role)
                .status(this.status)
                .oauth(this.oauth)
                .build();
    }
}
