package kr.co.talk.global.service.redis;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.talk.domain.chatroom.dto.RoomEmoticon;
import kr.co.talk.global.constants.RedisConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {
	private final StringRedisTemplate stringRedisTemplate;
	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;

	private ValueOperations<String, String> valueOps;
	private ListOperations<String, String> opsForList;

	@PostConstruct
	public void init() {
		valueOps = stringRedisTemplate.opsForValue();
		opsForList = redisTemplate.opsForList();
	}

	/**
	 * get value
	 * 
	 * @param key
	 * @return
	 */
	public String getValues(String key) {
		return valueOps.get(key);
	}

	/**
	 * set value with timeout
	 *
	 * @param key
	 * @param value
	 * @param timeout
	 */
	public void setValuesWithTimeout(String key, String value, long timeout) {
		stringRedisTemplate.opsForValue().set(key, value, Duration.ofMillis(timeout));
	}

	public void pushList(String key, Object value) {
		try {
			String item = objectMapper.writeValueAsString(value);
			opsForList.leftPush(key, item);
		} catch (JsonProcessingException e) {
			log.error("json parse exception , key is :: {}, value is :: {}", key, value, e);
			throw new RuntimeException(e);
		}
	}

	public List<String> getList(String key) {
		return opsForList.size(key) == 0 ? new ArrayList<>() : opsForList.range(key, 0, -1);
	}

	public List<RoomEmoticon> getEmoticonList(long chatroomId) {
		String key = chatroomId + RedisConstants.ROOM_EMOTICON;
		List<String> emoticonList = opsForList.size(key) == 0 ? new ArrayList<>() : opsForList.range(key, 0, -1);

		return emoticonList.stream().map(s -> {
			try {
				return objectMapper.readValue(s, RoomEmoticon.class);
			} catch (JsonProcessingException e) {
				log.error("json parse error", e);
				throw new RuntimeException(e);
			}
		}).collect(Collectors.toList());

	}
}
