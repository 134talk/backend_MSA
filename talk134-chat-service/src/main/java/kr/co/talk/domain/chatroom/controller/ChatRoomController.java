package kr.co.talk.domain.chatroom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.talk.domain.chatroom.dto.ChatDto;
import kr.co.talk.domain.chatroom.dto.ChatEndChatroomDto;
import kr.co.talk.domain.chatroom.service.ChatRoomService;
import kr.co.talk.global.config.ChatEndChatroomDtoDeserializer;
import kr.co.talk.global.config.redis.RedisConfig;
import kr.co.talk.global.constants.KafkaConstants;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.talk.domain.chatroom.service.ChatRoomSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatRoomController {
	private final ChatRoomSender chatRoomSender;
	private final SimpMessagingTemplate simpMessagingTemplate;
	private final ObjectMapper objectMapper;
	private final RedisConnectionFactory redisConnectionFactory;
	private final RedisConfig redisConfig;

	@GetMapping("/send")
	public String send() throws JsonProcessingException {
		chatRoomSender.sendEndChatting(32L);
//	    chatRoomSender.test_send();
	    return "성공";
	}

	@KafkaListener(topics = KafkaConstants.CHAT_TOPIC, groupId = KafkaConstants.CHAT_TOPIC)
	@MessageMapping("/chat/sendMessage") // kafka에 넘겨서 kafka 에서 받아서 stomp로 뿌리는 (front쪽은 stomp로 넘겨줌 )
	//url 분기
	public void consumeChatMessage(String message, @Payload String type) throws JsonProcessingException {
		ChatEndChatroomDto chatEndChatroomDTO = objectMapper.readValue(message, ChatEndChatroomDto.class);
		long roomId = chatEndChatroomDTO.getRoomId();
		String lastChatSenderName = chatEndChatroomDTO.getLastChatSenderName();
		String lastChatMessage = chatEndChatroomDTO.getLastChatMessage();

		// Create ChatDto
		ChatDto lastChat = ChatDto.builder()
				.chatId(1L)
				.chatRoomId(roomId)
				.name(lastChatSenderName)
				.message(lastChatMessage)
				.build();

		//redis push
		RedisTemplate<String, ChatDto> redisTemplate = redisConfig.chatDtoRedisTemplate(redisConnectionFactory);

		ListOperations<String, ChatDto> listOps = redisTemplate.opsForList();
		listOps.rightPush("chat_room_" + roomId, lastChat);
		List<ChatDto> chatDtoList = listOps.range("chat_room_" + roomId, 0, -1);

		//websocket stomp sub destination set
		List<ChatDto> chatList = chatDtoList.stream()
				.map(chat -> {
					try {
						if (!chat.getMessage().startsWith("{")) {
							// If the message is a plain string, create ChatDto
							return ChatDto.builder()
									.chatId(chat.getChatId())
									.chatRoomId(chat.getChatRoomId())
									.name(chat.getName())
									.message(chat.getMessage())
									.build();
						}
						return objectMapper.readValue(chat.getMessage(), ChatDto.class);
					} catch (JsonProcessingException e) {
						log.error("Failed to parse chat message from Redis: {}", e.getMessage());
						return null;
					}
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toList());

		if (type.equalsIgnoreCase("ENTER")) {
			simpMessagingTemplate.convertAndSend("/sub/chat/room", chatList);
		}
		log.info("kafka consumer lastMessage={}", message);
		log.info("websocket return message={}", chatList);


//		// RedisTemplate과 MongoTemplate 생성
//		RedisTemplate<String, ChatDto> redisTemplate = redisConfig.chatDtoRedisTemplate(redisConnectionFactory);
//		MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, "chat_db");
//
//		// Chat room ID를 지정하여 Redis에서 채팅 내역 조회
//		List<ChatDto> chatDtoList = redisTemplate.opsForList().range("chat_room_" + roomId, 0, -1);
//
//		// MongoDB에 채팅 내역 저장
//		mongoTemplate.insertAll(chatDtoList.stream()
//				.map(chatDto -> {
//					return new ChatDocument(
//
//							chatDto.getChatRoomId(),
//
//					);
//				})
//				.collect(Collectors.toList()), "chat_room_" + roomId);

//		// Redis에서 채팅 내역 삭제
//		redisTemplate.delete("chat_room_" + roomId);
	}
}
