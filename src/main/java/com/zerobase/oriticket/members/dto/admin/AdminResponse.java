package com.zerobase.oriticket.members.dto.admin;

import com.zerobase.global.constants.UserRole;
import com.zerobase.oriticket.members.entity.Admin;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminResponse {

    private String email;
    private String name;
    private String nickname;
    private LocalDateTime registeredAt;

    public static Admin fromEntity(com.zerobase.oriticket.members.entity.Admin admin) {
        return Admin.builder()
                .email(admin.getEmail())
                .name(admin.getName())
                .nickname(admin.getNickname())
                .registeredAt(admin.getRegisteredAt())
                .build();
    }
}

