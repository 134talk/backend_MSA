package kr.co.talk.domain.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.talk.domain.user.dto.AuthTokenDto;
import kr.co.talk.domain.user.dto.LoginDto;
import kr.co.talk.domain.user.dto.SocialKakaoDto;
import kr.co.talk.global.exception.CustomException;
import kr.co.talk.domain.user.model.User;
import kr.co.talk.global.exception.CustomError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class SocialKakaoService {

    private final UserService userService;
    private final AuthService authService;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Value("${kakao.url.token}")
    private String tokenUrl;

    @Value("${kakao.url.profile}")
    private String profileUrl;

    public LoginDto login(SocialKakaoDto.TokenRequest requestDto) throws Exception {

        //액세슨 토큰 발급
        SocialKakaoDto.TokenResponse tokenResponseDto = getAccessToken(requestDto);

        //액세스 토큰으로 유저 정보 조회
        SocialKakaoDto.UserInfo userInfo = getUserInfo(tokenResponseDto.getAccess_token());

        //유저 정보 없으면 유저 생성 먼저 진행
        User user = userService.findByUserUid(userInfo.getId());
        if(user == null){
            user = userService.createUser(userInfo);
        }
        //jwt 토큰 발급
        //accesstoken userId로 발급
        AuthTokenDto authToken = authService.createAuthToken(user.getUserId());

        LoginDto loginDto = new LoginDto();
        loginDto.setUserId(user.getUserId());
        loginDto.setAccessToken(authToken.getAccessToken());
        loginDto.setRefreshToken(authToken.getRefreshToken());
        loginDto.setNickname(user.getNickname());
        loginDto.setTeamCode(user.getTeamCode());

        return loginDto;

    }

    /**
     * 액세스 토큰 조회
     * **/
    private SocialKakaoDto.TokenResponse getAccessToken(SocialKakaoDto.TokenRequest requestDto) throws Exception {
        //header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //param
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("client_id", requestDto.getClientId());
        paramMap.add("redirect_uri", requestDto.getRedirectUrl());
        paramMap.add("code", requestDto.getCode());
        paramMap.add("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity = null;

        //http 요청
        try {
            responseEntity = restTemplate.postForEntity(tokenUrl, new HttpEntity<>(paramMap, headers),
                    String.class);
        } catch (HttpClientErrorException e) {
            log.error("",e);
            throw new CustomException(CustomError.AUTH_TOKEN_CREATE_FAIL);
        }

        return objectMapper.readValue(responseEntity.getBody(), SocialKakaoDto.TokenResponse.class);

    }

    /**
     * 액세스 토큰으로 유저 정보 조회
     * **/
    private SocialKakaoDto.UserInfo getUserInfo(String token) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        //header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        //param
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();

        ResponseEntity<String> responseEntity = null;

        //http 요청
        try {
            responseEntity = restTemplate.postForEntity(profileUrl, new HttpEntity<>(paramMap, headers),
                    String.class);
        } catch (HttpClientErrorException e) {
            log.error("",e);
            throw new CustomException(CustomError.AUTH_TOKEN_CREATE_FAIL);
        }
        return objectMapper.readValue(responseEntity.getBody(), SocialKakaoDto.UserInfo.class);
    }
}