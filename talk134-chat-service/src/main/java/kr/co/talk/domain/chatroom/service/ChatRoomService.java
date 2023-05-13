package kr.co.talk.domain.chatroom.service;

import kr.co.talk.domain.chatroom.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    public ChatDto getLastChatMessage(long roomId) {
        ChatDto chatDto = new ChatDto();
        chatDto.setChatId(1L);
        chatDto.setChatRoomId(roomId);
        chatDto.setName("ari");
        chatDto.setMessage("Hello");

        return chatDto;
    }
}