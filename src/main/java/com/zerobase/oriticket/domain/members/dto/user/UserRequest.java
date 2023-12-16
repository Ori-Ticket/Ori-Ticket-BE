package com.zerobase.oriticket.domain.members.dto.user;

import com.zerobase.oriticket.domain.members.constants.MemberStatus;
import com.zerobase.oriticket.domain.members.constants.RoleType;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.zerobase.oriticket.domain.members.entity.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {



    private String email;
    private String name;
    private String nickname;
    private LocalDateTime birthDate;
    private String phoneNum;
    private MemberStatus status;
    private String oauth;
    private String password;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .name(this.name)
                .nickname(this.nickname)
                .birthDate(this.birthDate)
                .phoneNum(this.phoneNum)
                .registeredAt(Timestamp.valueOf(LocalDateTime.now()))
                .oauth(this.oauth)
                .build();
    }

    public User toEntityKakao() {
        return User.builder()
                .email(this.email)
                .nickname(this.nickname)
                .password(this.password)
                .oauth(this.oauth)
                .build();
    }
}
