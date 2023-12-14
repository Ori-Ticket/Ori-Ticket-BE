package com.zerobase.oriticket.domain.members.controller;

import com.zerobase.oriticket.domain.members.dto.user.UserRequest;
import com.zerobase.oriticket.domain.members.dto.user.UserResponse;
import com.zerobase.oriticket.domain.members.model.KakaoProfile;
import com.zerobase.oriticket.domain.members.model.OAuthToken;
import com.zerobase.oriticket.domain.members.service.KakaoAuthService;
import com.zerobase.oriticket.domain.members.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class UserController {

    @Autowired
    KakaoAuthService kakaoAuthService;

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public String signup(String code) {

        OAuthToken oAuthToken = kakaoAuthService
                .requestKakaoToken(code);

        ResponseEntity<String> kakaoProfileResponse = kakaoAuthService
                .requestKakaoProfile(oAuthToken);

        KakaoProfile kakaoProfile = kakaoAuthService
                .registerOrUpdateKakaoUser(kakaoProfileResponse);

        kakaoAuthService.autoLogin(kakaoProfile, userService);

        return null;
    }


    @PostMapping("/sign")
    public String sign() {

        return null;
    }


    @PatchMapping("/modify")
    public ResponseEntity<UserResponse> modify(@RequestBody UserRequest userRequest) {

//        User user = UserService.modif

//        return ResponseEntity.status(HttpStatus.OK).body(UserResponse.fromEntity())
        return null;

    }

    @GetMapping("/check")
    public ResponseEntity<UserResponse> check() {

        return null;
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<UserResponse> withdraw() {

        return null;
    }


}

