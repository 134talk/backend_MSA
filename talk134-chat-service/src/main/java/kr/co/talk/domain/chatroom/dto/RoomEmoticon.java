package kr.co.talk.domain.chatroom.dto;

import kr.co.talk.domain.chatroom.model.EmoticonCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 대화중 이모티콘을 날리면 redis에 저장하기위한 class
 */
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomEmoticon {
	private long roomId;
	private EmoticonCode emoticonCode;
	private long toUserId;
	private long fromUserId;
}
