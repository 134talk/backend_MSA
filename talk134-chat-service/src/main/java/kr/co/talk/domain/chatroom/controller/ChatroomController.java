package kr.co.talk.domain.chatroom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.talk.domain.chatroom.dto.ChatDto;
import kr.co.talk.domain.chatroom.dto.ChatroomSendDto;
import kr.co.talk.domain.chatroom.service.ChatRedisService;
import kr.co.talk.global.constants.KafkaConstants;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.talk.domain.chatroom.service.ChatRoomSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatroomController {
	private final ChatRoomSender chatRoomSender;
	private final ObjectMapper objectMapper;
	private final ChatRedisService chatRedisService;

	@GetMapping("/send")
	public String send() throws JsonProcessingException {
		chatRoomSender.sendEndChatting(32L);
//	    chatRoomSender.test_send();
		return "성공";
	}

	@GetMapping("/sendMessage")
	public List<ChatDto> sendMessage() throws JsonProcessingException {
		return chatRoomSender.sendMessageToKafka(ChatroomSendDto.builder().roomId(32L).userId(1L).build());
	}
}