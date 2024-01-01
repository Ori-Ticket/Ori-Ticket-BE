package com.zerobase.oriticket.domain.members.dto.user;

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
public class UserResponse {

    private Boolean existsByEmail;
    private String email;


    public UserResponse toEntity(UserRequest userRequest) {
        return UserResponse.builder()
                .existsByEmail(userRequest.getExistsByEmail())
                .email(userRequest.getEmail())
                .build();
    }

}
