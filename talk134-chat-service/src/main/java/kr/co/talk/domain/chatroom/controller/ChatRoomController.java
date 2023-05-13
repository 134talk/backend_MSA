package kr.co.talk.domain.chatroom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.talk.domain.chatroom.service.ChatRoomSender;
import kr.co.talk.global.client.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatRoomController {
	private final ChatRoomSender chatRoomSender;
	private final UserClient userClient;

	@GetMapping("/send")
	public String send() throws JsonProcessingException {
		chatRoomSender.sendEndChatting(32L);
		// chatRoomSender.test_send();
		return "성공";
	}


	@GetMapping("/user-call")
	public String getUser() {
		return userClient.getUser();
	}
}