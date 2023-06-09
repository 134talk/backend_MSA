package kr.co.talk.domain.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    private String name;
    private String nickname;
    private boolean activeFlag;
}

