package com.zerobase.oriticket.domain.members.dto.admin;

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
public class AdminResponse {

    private String email;
    private String name;
    private String nickname;
    private Long adminId;
    private LocalDateTime registeredAt;

    public AdminResponse fromEntity() {
        return AdminResponse.builder()
                .adminId(this.adminId)
                .email(this.email)
                .name(this.name)
                .nickname(this.nickname)
                .registeredAt(this.registeredAt)
                .build();
    }
}

