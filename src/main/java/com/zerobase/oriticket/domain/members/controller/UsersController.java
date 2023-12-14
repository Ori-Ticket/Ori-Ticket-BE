//package com.zerobase.oriticket.domain.members.controller;
//
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.zerobase.oriticket.domain.members.entity.User;
//import com.zerobase.oriticket.domain.members.model.KakaoProfile;
//import com.zerobase.oriticket.domain.members.model.OAuthToken;
//import com.zerobase.oriticket.domain.members.service.UserService;
//import java.util.UUID;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.client.RestTemplate;
//
//
//@Controller
//public class UsersController {
//
//    @Value("1234")
//    private String encryption;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserService userService;
//
////    @GetMapping("/auth/joinForm")
////    public String joinForm() {
////        return "user/joinForm";
////    }
////
////    @GetMapping("/auth/loginForm")
////    public String loginForm() {
////        return "user/loginForm";
////    }
//
//    @GetMapping("/auth/kakao/callback")
//    public @ResponseBody String kakaoCallback(String code) {
//        RestTemplate rt = new RestTemplate();
//
////        HttpHeader 오브젝트 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
////        HttpBody 오브젝트 생성
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", "authorization_code");
//        params.add("client_id", "0f5dbcca74a5d4028b0110d4e1201c8d");
//        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
//        params.add("code", code);
//
////        HttpHeader, HttpBody 를 하나의 오브젝트에 담기
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
//
////        Http 요청하기 - Post 방식으로 - Response 변수의 응답 받음
//        ResponseEntity<String> response = rt.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                kakaoTokenRequest,
//                String.class);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        OAuthToken oauthToken = null;
//        try {
//            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
//        } catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
////        System.out.println("카카오 엑세스 토큰 : " + oauthToken.getAccess_token());
//
//        RestTemplate rt2 = new RestTemplate();
//
////        HttpHeader 오브젝트 생성
//        HttpHeaders headers2 = new HttpHeaders();
//        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
//        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
////        HttpHeader, HttpBody 를 하나의 오브젝트에 담기
//        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);
//
////        Http 요청하기 - Post 방식으로 - Response 변수의 응답 받음
//        ResponseEntity<String> response2 = rt2.exchange(
//                "https://kapi.kakao.com/v2/user/me",
//                HttpMethod.POST,
//                kakaoProfileRequest2,
//                String.class);
//
////        System.out.println(response2.getBody());
//
//        ObjectMapper objectMapper2 = new ObjectMapper();
//        KakaoProfile kakaoProfile = null;
//        try {
//            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
//        } catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        // User 오브젝트 : username, password, email
//        System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
//        System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
//        System.out.println("블로그서버 유저네임 : " + kakaoProfile.getKakao_account().getProfile().getNickname());
//        // UUID란 -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘
//        UUID garbagePassword = UUID.randomUUID();
//        System.out.println("블로그서버 패스워드 : " + garbagePassword);
//
//        User kakaoUser = User.builder()
//                .username(kakaoProfile.getKakao_account().getProfile().getNickname())
//                .password(encryption)
//                .email(kakaoProfile.getKakao_account().getEmail())
//                .oauth("kakao")
//                .build();
//
//        // 가입자 혹은 비가입자 체크 해서 처리
////        User originUser = userService.회원찾기(kakaoUser.getUsername());
//        Boolean originUser = userService.회원찾기(kakaoProfile.getKakao_account().getEmail());
//        if (!originUser) {
//            System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다");
//            userService.회원가입(kakaoUser);
//        }
//
//        System.out.println("자동 로그인을 진행합니다.");
//        // 로그인 처리
////        Authentication authentication = authenticationManager.authenticate(
////                new UsernamePasswordAuthenticationToken(kakaoUser.getEmail(), encryption));
////        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return "회원가입완료";
//
//    }
//
////    @GetMapping("/user/updateForm")
////    public String updateForm() {
////        return "user/updateForm";
////    }
//}
