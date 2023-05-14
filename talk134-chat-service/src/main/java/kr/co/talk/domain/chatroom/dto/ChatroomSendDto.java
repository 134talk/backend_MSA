package kr.co.talk.domain.chatroom.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatroomSendDto {
    private Long roomId;
    private Long userId;
}