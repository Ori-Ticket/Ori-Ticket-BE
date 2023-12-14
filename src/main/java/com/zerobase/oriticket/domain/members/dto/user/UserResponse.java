package com.zerobase.oriticket.domain.members.dto.user;

import com.zerobase.oriticket.domain.members.constants.MemberStatus;
import com.zerobase.oriticket.domain.members.constants.RoleType;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private String email;
    private String name;
    private String nickname;
    private LocalDateTime birthDate;
    private String phoneNum;
    private RoleType role;
    private MemberStatus status;
    private LocalDateTime registeredAt;
    private Timestamp modifiedAt;
    private String oauth;

    public UserResponse fromEntity() {
        return UserResponse.builder()
                .email(this.email)
                .name(this.name)
                .nickname(this.nickname)
                .birthDate(this.birthDate)
                .phoneNum(this.phoneNum)
                .role(this.role)
                .status(this.status)
                .registeredAt(this.registeredAt)
                .modifiedAt(Timestamp.valueOf(LocalDateTime.now()))
                .oauth(this.oauth)
                .build();
    }
}
