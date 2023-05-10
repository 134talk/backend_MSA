package kr.co.talk.domain.user.controller;

import kr.co.talk.domain.user.dto.AuthTokenDto;
import kr.co.talk.domain.user.dto.SocialKakaoDto;
import kr.co.talk.domain.user.service.AuthService;
import kr.co.talk.domain.user.service.SocialKakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final SocialKakaoService socialKakaoService;

    //카카오 로그인
    @PostMapping("/login/kakao")
    public ResponseEntity loginKakao(@RequestBody SocialKakaoDto.TokenRequest request) throws Exception {
        return new ResponseEntity(socialKakaoService.login(request),HttpStatus.OK);

    }

    //토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity tokenRefresh(AuthTokenDto requestDto){
        return new ResponseEntity(authService.tokenRefresh(requestDto), HttpStatus.OK);
    }

//    //로그아웃
//    @PostMapping("/logout")
//    public ResponseEntity logout() throws Exception {
//        authService.logout(userId);
//        return new ResponseEntity(socialKakaoService.login(request),HttpStatus.OK);
//
//    }

    @GetMapping("/test")
    public ResponseEntity tokenRefres12h(){
        return new ResponseEntity(authService.createAuthToken(1l), HttpStatus.OK);
    }
}
