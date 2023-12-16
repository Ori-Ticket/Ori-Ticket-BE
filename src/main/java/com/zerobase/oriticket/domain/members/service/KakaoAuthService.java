package com.zerobase.oriticket.domain.members.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.oriticket.domain.members.dto.user.UserRequest;
import com.zerobase.oriticket.domain.members.entity.User;
import com.zerobase.oriticket.domain.members.model.KakaoProfile;
import com.zerobase.oriticket.domain.members.model.OAuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

//@Service
//public class KakaoAuthService {
//
//    public OAuthToken requestKakaoToken(String code) {
//
//        RestTemplate rt = new RestTemplate();
//
//        //        HttpHeader 오브젝트 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        //        HttpBody 오브젝트 생성
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", "authorization_code");
//        params.add("client_id", "0f5dbcca74a5d4028b0110d4e1201c8d");
//        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
//        params.add("code", code);
//
//        //        HttpHeader, HttpBody 를 하나의 오브젝트에 담기
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
//
//        //        Http 요청하기 - Post 방식으로 - Response 변수의 응답 받음
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
//        return oauthToken;
//
////        System.out.println("카카오 엑세스 토큰 : " + oauthToken.getAccess_token());
//    }
//
//    public ResponseEntity<String> requestKakaoProfile(OAuthToken oAuthToken) {
//        // 카카오 프로필을 얻기 위한 REST 요청 준비
//        RestTemplate rt2 = new RestTemplate();
//
////        HttpHeader 오브젝트 생성
//        HttpHeaders headers2 = new HttpHeaders();
//        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
//        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
////        HttpHeader, HttpBody 를 하나의 오브젝트에 담기
//        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);
//
////        Http 요청하기 - Post 방식으로 - Response 변수의 응답 받음
//        ResponseEntity<String> kakaoProfileResponse = rt2.exchange(
//                "https://kapi.kakao.com/v2/user/me",
//                HttpMethod.POST,
//                kakaoProfileRequest2,
//                String.class);
//
//        System.out.println(kakaoProfileResponse.getBody());
//        return kakaoProfileResponse;
//
//    }
//
//    public KakaoProfile registerOrUpdateKakaoUser(ResponseEntity<String> kakaoProfileResponse) {
//        // ... 사용자 등록 또는 업데이트 로직을 여기에 구현
//        ObjectMapper objectMapper2 = new ObjectMapper();
//        KakaoProfile kakaoProfile = null;
//        try {
//            kakaoProfile = objectMapper2.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
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
//        return kakaoProfile;
//    }
//
//    public String autoLogin(KakaoProfile kakaoProfile, UserService userService) {
//        // ... 자동 로그인 처리 로직을 여기에 구현
//
//        User kakaoUser = User.builder()
//                .nickname(kakaoProfile.getKakao_account().getProfile().getNickname())
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
//    }

@Service
public class KakaoAuthService {


    public static final String KAKAO_OAUTH_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    public static final String KAKAO_USER_ME_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String CONTENT_TYPE_HEADER = "Content-type";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded;charset=utf-8";
    private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    private static final String CLIENT_ID = "0f5dbcca74a5d4028b0110d4e1201c8d";
    private static final String REDIRECT_URI = "http://localhost:8080/members/kakao/login";
    private final RestTemplate restTemplate = new RestTemplate();

    private AuthenticationManager authenticationManager ;

    @Value("${ori.key}")
    private String oriKey;

    public OAuthToken requestKakaoToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE_HEADER, APPLICATION_X_WWW_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE_AUTHORIZATION_CODE);
        params.add("client_id", CLIENT_ID);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(KAKAO_OAUTH_TOKEN_URL, HttpMethod.POST,
                kakaoTokenRequest, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;

        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return oauthToken;
    }

    public ResponseEntity<String> requestKakaoProfile(OAuthToken oAuthToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, "Bearer " + oAuthToken.getAccess_token());
        headers.add(CONTENT_TYPE_HEADER, APPLICATION_X_WWW_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        return restTemplate.exchange(KAKAO_USER_ME_URL, HttpMethod.POST, kakaoProfileRequest,
                String.class);
    }

    public KakaoProfile registerOrUpdateKakaoUser(ResponseEntity<String> kakaoProfileResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return kakaoProfile;
    }

    public UserRequest buildKakaoUser(KakaoProfile kakaoProfile) {
        System.out.println("고유식별번호 : " + kakaoProfile.getId());
        System.out.println("이메일 : " + kakaoProfile.getKakao_account().getEmail());
        System.out.println("이름 : " + kakaoProfile.getKakao_account().getProfile().getNickname());
        System.out.println("패스워드 : " + oriKey);
        return UserRequest.builder()
                .email(kakaoProfile.getKakao_account().getEmail())
                .nickname(kakaoProfile.getKakao_account().getProfile().getNickname())
                .password(oriKey)
                .oauth("kakao")
                .build();
    }

    public Boolean autoLogin(KakaoProfile kakaoProfile, UserService userService) {
        return userService.findUser(kakaoProfile.getKakao_account().getEmail());
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(kakaoProfile.getKakao_account().getEmail(), oriKey));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return "회원가입완료";
    }

}



