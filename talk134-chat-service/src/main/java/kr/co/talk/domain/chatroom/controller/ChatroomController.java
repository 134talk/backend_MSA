package kr.co.talk.domain.chatroom.controller;

import kr.co.talk.domain.chatroom.dto.ChatDto;
import kr.co.talk.domain.chatroom.dto.ChatroomListDto;
import kr.co.talk.domain.chatroom.dto.ChatroomSendDto;
import kr.co.talk.global.client.UserClient;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.talk.domain.chatroom.service.ChatRoomSender;
import kr.co.talk.domain.chatroom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatroomController {

	private final ChatRoomSender chatRoomSender;
	private final UserClient userClient;
	private final ChatRoomService chatRoomService;


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

	@GetMapping("/test")
	public String test(@RequestHeader(value = "userId") String userId) {
		log.info("userId::" + userId);
		return "test";
	}

	@GetMapping("/sendMessage")
	public List<ChatDto> sendMessage() throws JsonProcessingException {
		return chatRoomSender.sendMessageToKafka(ChatroomSendDto.builder().roomId(32L).userId(1L).build());
	}

	/**
	 * 대화방 목록 조회 api
	 * 
	 * @param userId
	 * @param teamCode
	 * @return
	 */
	@GetMapping("/find-chatrooms")
	public ResponseEntity<?> findChatRooms(@RequestHeader(value = "userId") String userId, String teamCode) {
		return ResponseEntity.ok(chatRoomService.findChatRooms(Long.valueOf(userId), teamCode));
	}
	
	/**
	 * 닉네임 또는 이름으로 대화방 목록 조회 api
	 * 
	 * @param userId
	 * @param teamCode
	 * @param name
	 * @return
	 */
	@GetMapping("/find-chatrooms-with-name")
	public ResponseEntity<?> findChatRoomsWithName(@RequestHeader(value = "userId") String userId, String teamCode, String name) {
		return ResponseEntity.ok(chatRoomService.findChatRoomsByName(Long.valueOf(userId), teamCode, name));
	}

	/**
	 * 대화방 생성 api
	 * 
	 * @param teamCode
	 * @param userList
	 * @return
	 */
	@PostMapping("/create-chatroom")
	public ResponseEntity<?> createChatroom(@RequestBody List<Long> userList) {
		// teamCode 받을것인지..
		chatRoomService.createChatroom("team_code", userList);
		return ResponseEntity.ok().build();
	}

	
}
