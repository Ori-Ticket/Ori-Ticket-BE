package com.zerobase.oriticket.domain.members.controller;

import com.zerobase.oriticket.domain.members.dto.user.UserRequest;
import com.zerobase.oriticket.domain.members.dto.user.UserResponse;
import com.zerobase.oriticket.domain.members.entity.User;
import com.zerobase.oriticket.domain.members.model.KakaoProfile;
import com.zerobase.oriticket.domain.members.model.OAuthToken;
import com.zerobase.oriticket.domain.members.repository.UserRepository;
import com.zerobase.oriticket.domain.members.service.KakaoAuthService;
import com.zerobase.oriticket.domain.members.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/members")
public class UserController {

    @Autowired
    KakaoAuthService kakaoAuthService;

    @Autowired
    UserService userService;

    @GetMapping("/kakao/login")
    public String handleKakaoSignup(String code) {

        OAuthToken oAuthToken = kakaoAuthService.requestKakaoToken(code);

        ResponseEntity<String> kakaoProfileResponse = kakaoAuthService.requestKakaoProfile(oAuthToken);

        KakaoProfile kakaoProfile = kakaoAuthService.registerOrUpdateKakaoUser(kakaoProfileResponse);

        User user = kakaoAuthService.buildKakaoUser(kakaoProfile);

        Boolean isMember = kakaoAuthService.autoLogin(kakaoProfile, userService);
        System.out.println("isMember = " + isMember);

        if (!isMember) {
            userService.registerUser(user);
            return "redirect:/members/signup";
        }
        return "redirect:/members/signin";
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody UserRequest userRQ


    ) {
        userService.registerUser(userRQ);
        return "redirect:/home";
    }

    @GetMapping("/signup")
    public String signUp() {
        System.out.println("로그인정보 없어서 회원가입");
        return "redirect:/home";
    }

    @GetMapping("/signin")
    public String signIn() {
        System.out.println("로그인정보 있어서 로그인");
        return "home";
    }


    @PatchMapping("/modify")
    public ResponseEntity<UserResponse> modify(@RequestBody UserRequest userRequest) {

        return null;

    }

    @GetMapping("/check")
    public ResponseEntity<UserResponse> check() {

        return null;
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<UserResponse> withDraw() {

        return null;
    }


}

