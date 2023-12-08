package com.zerobase.oriticket.domain.members.dto.admin;

import com.zerobase.oriticket.domain.members.constants.UserRole;
import com.zerobase.oriticket.domain.members.entity.Admin;
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
public class AdminRequest {

    private String email;
    private String name;
    private String nickname;

    public Admin toEntity() {
        return Admin.builder()
                .email(this.email)
                .name(this.name)
                .nickname(this.nickname)
                .roles(UserRole.ADMIN)
                .registeredAt(LocalDateTime.now())
                .build();
    }
}



