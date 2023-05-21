package kr.co.talk.redis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import kr.co.talk.domain.chatroom.dto.RoomEmoticon;
import kr.co.talk.domain.chatroom.model.EmoticonCode;
import kr.co.talk.global.constants.RedisConstants;
import kr.co.talk.global.service.redis.RedisService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;

@SpringBootTest
public class RedisServiceTest {

	@Autowired
	private RedisService redisService;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("Redis set value with timeout test")
	void setValuesWithTimeoutTest() {
		// given
		final String key = "test_key";
		final String value = "test_value";

		// when
		redisService.setValuesWithTimeout(key, value, 3000);

		// then
		assertNotNull(redisService.getValues(key));

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNull(redisService.getValues(key));
	}

	@Test
	@DisplayName("Redis list set and get")
	void redisListTest() throws JsonProcessingException {
		// given
		long roomId = 512312312312123L;
		long fromUserId = 1521431231L;
		long toUserId = 23423324l;
		String key = roomId + RedisConstants.ROOM_EMOTICON;
		RoomEmoticon value = RoomEmoticon.builder().roomId(roomId).emoticonCode(EmoticonCode.EMOTICON_TP1)
				.toUserId(toUserId).fromUserId(fromUserId).build();

		// when
		redisService.pushList(key, value);
		List<String> range = redisService.getList(key);
		RoomEmoticon roomEmoticon = objectMapper.readValue(range.get(0), RoomEmoticon.class);

		// then
		assertEquals(range.size(), 1);
		assertEquals(roomEmoticon.getRoomId(), roomId);
		assertEquals(roomEmoticon.getEmoticonCode(), EmoticonCode.EMOTICON_TP1);
		assertEquals(roomEmoticon.getFromUserId(), fromUserId);
		assertEquals(roomEmoticon.getToUserId(), toUserId);

		redisTemplate.delete(key);
	}

}
