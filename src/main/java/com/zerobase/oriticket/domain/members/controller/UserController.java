package com.zerobase.oriticket.domain.members.controller;

import com.zerobase.oriticket.domain.members.dto.user.UserRequest;
import com.zerobase.oriticket.domain.members.entity.Member;
import com.zerobase.oriticket.domain.members.model.KakaoProfile;
import com.zerobase.oriticket.domain.members.model.OAuthToken;
import com.zerobase.oriticket.domain.members.service.KakaoAuthService;
import com.zerobase.oriticket.domain.members.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
//@AllArgsConstructor
//@NoArgsConstructor
//@RequiredArgsConstructor
public class UserController {

    @Autowired
    private KakaoAuthService kakaoAuthService;
    @Autowired
    private UserService userService;

    private UserRequest kakaoUser;

    @GetMapping("/kakao/login")
    public ResponseEntity<KakaoProfile> handleKakao(@RequestParam String code) {
        OAuthToken oAuthToken = kakaoAuthService.requestKakaoToken(code);
        ResponseEntity<String> kakaoProfileResponse = kakaoAuthService.requestKakaoProfile(oAuthToken);
        KakaoProfile kakaoProfile = kakaoAuthService.registerOrUpdateKakaoUser(kakaoProfileResponse);
//        UserResponse userResponse = UserResponse.builder()
//                .email(kakaoProfile.getKakao_account().getEmail())
//                .nickname(kakaoProfile.getKakao_account().getProfile().getNickname())
//                .build();
//        kakaoUser = kakaoAuthService.buildKakaoUser(kakaoProfile);
        return ResponseEntity.ok(kakaoProfile);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserRequest> signup(@RequestBody UserRequest userRequest) {
        userService.updateUserByEmail(kakaoUser, userRequest);
        return ResponseEntity.ok(userRequest);
    }

    @PostMapping("/signin")
    public ResponseEntity<Boolean> signin(@RequestBody UserRequest userRequest) {
        System.out.println("로그인");
        boolean byUser = userService.findByEmail(userRequest.getEmail());
        return ResponseEntity.ok(byUser);
    }

    @PatchMapping("/modify")
    public ResponseEntity<Member> updateUser(@RequestBody UserRequest userRequest) {
        System.out.println("회원수정");
        Member member = userService.updateUser(userRequest);
        return ResponseEntity.ok(member);
    }

    @GetMapping("/check/{id}")
    public ResponseEntity<Member> checkUser(@PathVariable long id) {
        System.out.println("회원정보확인");
        Member member = userService.checkUser(id);
        return ResponseEntity.ok().body(kakaoUser.toEntityKakao());
//        return ResponseEntity.ok(member);
    }

    @DeleteMapping("/withdraw/{id}")
    public ResponseEntity<Member> deleteUser(@PathVariable long id) {
        System.out.println("회원탈퇴");
        Member member = userService.deleteUser(id);
        return ResponseEntity.ok(member);
    }
}

