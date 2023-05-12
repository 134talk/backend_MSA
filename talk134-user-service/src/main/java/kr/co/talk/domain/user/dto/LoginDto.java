package kr.co.talk.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

    public String accessToken;
    public String refreshToken;
    public Long userId;
    public String nickname;
    public String teamCode;
}
