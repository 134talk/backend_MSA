package kr.co.talk.domain.chatroom.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.talk.domain.chatroom.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import static kr.co.talk.global.constants.RedisConstants.*;
import javax.annotation.PostConstruct;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRedisService {
	@Autowired
	private RedisTemplate<String, ChatDto> redisTemplate;
	@Autowired
	private RedisTemplate<String, String> redisStringTemplate;
	private ValueOperations<String, String> valueOps;
	private ListOperations<String, ChatDto> listOpsChatDto;
	private ObjectMapper objectMapper;

	@PostConstruct
	private void init() {
		valueOps = redisStringTemplate.opsForValue();
		listOpsChatDto = redisTemplate.opsForList();
	}

	public List<ChatDto> sendChatroomDto(Long roomId, ChatDto lastChat) {
		listOpsChatDto.rightPush(CHAT_ROOM + roomId, lastChat);
		List<ChatDto> chatDtoList = listOpsChatDto.range(CHAT_ROOM + roomId, 0, -1);

		return chatDtoList.stream().map(chat -> {
			try {
				if (!chat.getName().startsWith("{")) {
					// if message null -> create one
					return ChatDto.builder().name(chat.getName()).nickname(chat.getNickname()).activeFlag(true).build();
				}
				return objectMapper.readValue(chat.getName(), ChatDto.class);
			} catch (JsonProcessingException e) {
				log.error("Failed to parse chat message from Redis: {}", e.getMessage());
				return null;
			}
		}).filter(Objects::nonNull).collect(Collectors.toList());
	}

	// userCount + 1
	public Long plusUserCount(Long roomId) {
		return Long.valueOf(Optional.ofNullable(valueOps.increment(USER_COUNT + roomId)).orElse(0L));
	}

	// userCount - 1
	public Long minusUserCount(Long roomId) {
		return Long.valueOf(
				Optional.ofNullable(valueOps.decrement(USER_COUNT + roomId)).filter(count -> count > 0).orElse(0L));
	}

}
