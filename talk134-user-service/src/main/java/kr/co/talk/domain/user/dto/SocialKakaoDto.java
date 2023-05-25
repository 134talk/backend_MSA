package kr.co.talk.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kr.co.talk.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialKakaoDto {

    /**
     * 액세스 토큰 요청시 사용
     */
    @Getter
    @Setter
    public static class TokenRequest {
        private String code;
        private String clientId;
        private String redirectUrl;
    }

    /**
     * 액세스 토큰 결과 파싱
     */
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TokenResponse {
        private String access_token;
        private String refresh_token;
    }

    /**
     * 액세스 토큰으로 카카오 유저 정보 조회시 사용
     */
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserInfo {
        private String id; //uid

        //UserInfo -> User Entity
        public User createUser(){
            User user = User.builder().userUid(id).role("ROLE_USER").build();
            return user;
        }
    }
}

