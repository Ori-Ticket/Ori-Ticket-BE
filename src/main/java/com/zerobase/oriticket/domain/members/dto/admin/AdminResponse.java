package com.zerobase.oriticket.domain.members.dto.admin;

import com.zerobase.oriticket.domain.members.constants.RoleType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminResponse {

    private String email;
    private String name;
    private String nickname;
    private Long adminId;
    private RoleType roles;
    private LocalDateTime registeredAt;
}

