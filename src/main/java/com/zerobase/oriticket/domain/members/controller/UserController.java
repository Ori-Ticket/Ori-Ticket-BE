package com.zerobase.oriticket.domain.members.controller;

import com.zerobase.oriticket.domain.members.dto.ResponseDto;
import com.zerobase.oriticket.domain.members.dto.user.UserRequest;
import com.zerobase.oriticket.domain.members.dto.user.UserResponse;
import com.zerobase.oriticket.domain.members.entity.User;
import com.zerobase.oriticket.domain.members.model.KakaoProfile;
import com.zerobase.oriticket.domain.members.model.OAuthToken;
import com.zerobase.oriticket.domain.members.repository.UserRepository;
import com.zerobase.oriticket.domain.members.service.KakaoAuthService;
import com.zerobase.oriticket.domain.members.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
public class UserController {

    @Autowired
    KakaoAuthService kakaoAuthService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/kakao/login")
    public String handleKakaoSignup(String code) {

        OAuthToken oAuthToken = kakaoAuthService.requestKakaoToken(code);

        ResponseEntity<String> kakaoProfileResponse = kakaoAuthService.requestKakaoProfile(oAuthToken);

        KakaoProfile kakaoProfile = kakaoAuthService.registerOrUpdateKakaoUser(kakaoProfileResponse);

        UserRequest userRequest = kakaoAuthService.buildKakaoUser(kakaoProfile);
        userService.registerUserKakao(userRequest);

        Boolean isMember = kakaoAuthService.autoLogin(kakaoProfile, userService);

//        if (!isMember) {
//            System.out.println("2222");
//            signup(user);
//            System.out.println("3333");
//            return "redirect:/members/signup";
//        }
//        signup(user);
//        return "redirect:/members/signin";

        return null;
    }

    //    @PostMapping("/signup")
//    public ResponseDto<Integer> signup(@RequestBody User user) {
//        System.out.println("4444");
//        System.out.println("signup 호출됨");
//        Boolean user1 = userService.findUser(user.getEmail());
//        System.out.println("5555");
//        if (!user1) {
//            userRepository.save(user);
//        }
//        return new ResponseDto<>(HttpStatus.OK.value(), 1);
//    }
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody UserRequest userRequest) {
        Boolean userFind = userService.findUser(userRequest.getEmail());
        if (userFind) {
            User user = userService.insert(userRequest);
            return ResponseEntity.status(HttpStatus.OK).body(UserResponse.fromEntity(user));

        } else {
            User user = userService.registerUser(userRequest);
            return ResponseEntity.status(HttpStatus.OK).body(UserResponse.fromEntity(user));
        }

    }

    @PostMapping("/signin")
    public ResponseDto<Integer> save(@RequestBody UserRequest user) {
        System.out.println("signin 호출됨");
//        userService.registerUser(user);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/modify")
    public ResponseDto<Integer> update(@RequestBody UserRequest user) {
        System.out.println("modify 호출됨");
//        userService.registerUser(user);
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getNickname(), user.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @GetMapping("/check")
    public ResponseDto<Integer> check(@RequestBody UserRequest user) {
        System.out.println("check 호출됨");
//        userService.registerUser(user);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/withdraw")
    public ResponseDto<Integer> withdraw(@RequestBody UserRequest user) {
        System.out.println("withdraw 호출됨");
//        userService.registerUser(user);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }


}

