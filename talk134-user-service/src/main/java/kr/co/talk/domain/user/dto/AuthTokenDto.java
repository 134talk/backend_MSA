package kr.co.talk.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 토큰 발급 응답
 * 재발급 요청시 사용
 * **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenDto {

    private String accessToken;
    private String refreshToken;
}
