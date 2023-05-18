package kr.co.talk.domain.chatroom.dto;

import kr.co.talk.domain.chatroom.model.EmoticonCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Emoticons {
    private EmoticonCode emoticonCode;
    private int emoticonCount;
}
