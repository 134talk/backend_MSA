package kr.co.talk.domain.chatroom.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 채팅방 끝나기 5분전에 알림주고, 채팅방 종료를 시켜주기위한 dto
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatroomNoticeDto {
    private long roomId;
    private long timeout;
    private long createTime;
}
