package kr.co.talk.domain.chatroom.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatroomSendDto {
    private Long roomId;
    private Long userId;
}