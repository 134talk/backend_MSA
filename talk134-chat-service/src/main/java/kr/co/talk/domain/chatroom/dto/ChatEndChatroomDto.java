package kr.co.talk.domain.chatroom.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatEndChatroomDto {
    private Long roomId;
    private String lastChatSenderName;
    private String lastChatMessage;
}