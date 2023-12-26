package com.zerobase.oriticket.domain.members.controller;

import com.zerobase.oriticket.domain.members.dto.user.UserRequest;
import com.zerobase.oriticket.domain.members.entity.UserEntity;
import com.zerobase.oriticket.domain.members.model.KakaoProfile;
import com.zerobase.oriticket.domain.members.model.OAuthToken;
import com.zerobase.oriticket.domain.members.service.KakaoAuthService;
import com.zerobase.oriticket.domain.members.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
public class UserController {

    @Autowired
    KakaoAuthService kakaoAuthService;

    @Autowired
    UserService userService;

    UserRequest kakaoUser;

    @GetMapping("/kakao/login")
    public ResponseEntity<UserRequest> handleKakao(String code) {
        OAuthToken oAuthToken = kakaoAuthService.requestKakaoToken(code);
        ResponseEntity<String> kakaoProfileResponse = kakaoAuthService.requestKakaoProfile(oAuthToken);
        KakaoProfile kakaoProfile = kakaoAuthService.registerOrUpdateKakaoUser(kakaoProfileResponse);
        kakaoUser = kakaoAuthService.buildKakaoUser(kakaoProfile);
        return ResponseEntity.ok(kakaoUser);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserRequest> signup(@RequestBody UserRequest userRequest) {
        userService.updateUserByEmail(kakaoUser, userRequest);
        return ResponseEntity.ok(userRequest);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserRequest> signin(@RequestBody UserRequest userRequest) {
        System.out.println("로그인");
        userService.findByEmail(userRequest.getEmail());
        return ResponseEntity.ok(userRequest);
    }

    @PatchMapping("/modify")
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserRequest userRequest) {
        System.out.println("회원수정");
        UserEntity userEntity = userService.updateUser(userRequest);
        return ResponseEntity.ok(userEntity);
    }

    @GetMapping("/check/{id}")
    public ResponseEntity<UserEntity> checkUser(@PathVariable long id) {
        System.out.println("회원정보확인");
        UserEntity userEntity = userService.checkUser(id);
        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/withdraw/{id}")
    public ResponseEntity<UserEntity> deleteUser(@PathVariable long id) {
        System.out.println("회원탈퇴");
        UserEntity userEntity = userService.deleteUser(id);
        return ResponseEntity.ok(userEntity);
    }
}

