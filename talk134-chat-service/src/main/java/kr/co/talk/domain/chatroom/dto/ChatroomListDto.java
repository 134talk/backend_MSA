package kr.co.talk.domain.chatroom.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 대화 목록 조회 dto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatroomListDto {
    private long roomId;
    private String roomName;
    private int userCount;
    private List<Emoticons> emoticons = new ArrayList<>();
    private boolean joinFlag;
}
