package com.zerobase.oriticket.domain.members.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.oriticket.domain.members.constants.MemberStatus;
import com.zerobase.oriticket.domain.members.constants.RoleType;
import com.zerobase.oriticket.domain.members.dto.user.UserRequest;
import com.zerobase.oriticket.domain.members.model.KakaoProfile;
import com.zerobase.oriticket.domain.members.model.OAuthToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoAuthService {

    public static final String KAKAO_OAUTH_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    public static final String KAKAO_USER_ME_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String CONTENT_TYPE_HEADER = "Content-type";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded;charset=utf-8";
    private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    private static final String CLIENT_ID = "0f5dbcca74a5d4028b0110d4e1201c8d";
    private static final String REDIRECT_URI = "http://13.124.46.138:8080/members/kakao/login";
    private final RestTemplate restTemplate = new RestTemplate();


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

        return restTemplate.exchange(KAKAO_USER_ME_URL, HttpMethod.POST, kakaoProfileRequest, String.class);
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
        System.out.println("권한 : " + RoleType.ROLE_USER);
        System.out.println("상태 : " + MemberStatus.ACTIVE);
        return UserRequest.builder().email(kakaoProfile.getKakao_account().getEmail())
                .nickname(kakaoProfile.getKakao_account().getProfile().getNickname()).password(oriKey).oauth("kakao")
                .role(RoleType.ROLE_USER).status(MemberStatus.ACTIVE).build();
    }


}



