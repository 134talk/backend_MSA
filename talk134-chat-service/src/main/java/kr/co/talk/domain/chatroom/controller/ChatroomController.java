package kr.co.talk.domain.chatroom.controller;

import kr.co.talk.domain.chatroom.dto.ChatDto;
import kr.co.talk.domain.chatroom.dto.ChatroomSendDto;
import kr.co.talk.global.client.UserClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.talk.domain.chatroom.service.ChatRoomSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatroomController {

    private final ChatRoomSender chatRoomSender;
    private final UserClient userClient;

    @GetMapping("/send")
    public String send() throws JsonProcessingException {
        chatRoomSender.sendEndChatting(32L);
//	    chatRoomSender.test_send();
        return "성공";
    }

    @GetMapping("/user-call")
    public String getUser() {
        return userClient.getUser();
    }

    @GetMapping("/test")
    public String test(@RequestHeader(value = "userId") String userId) {
        log.info("userId::"+userId);
        return "test";
    }

    @GetMapping("/sendMessage")
    public List<ChatDto> sendMessage() throws JsonProcessingException {
        return chatRoomSender.sendMessageToKafka(ChatroomSendDto.builder().roomId(32L).userId(1L).build());
    }
}
