package kr.co.talk.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDto {

    @Data
    public static class NameResponseDto{
        private String name;
    }

    @Data
    public static class TeamCodeResponseDto{
        private String teamCode;
    }
}
