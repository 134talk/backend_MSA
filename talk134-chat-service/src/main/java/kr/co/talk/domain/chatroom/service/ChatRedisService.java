package kr.co.talk.domain.chatroom.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.talk.domain.chatroom.dto.ChatDto;
import kr.co.talk.global.config.redis.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static kr.co.talk.domain.chatroom.constants.RedisConstants.*;

@Slf4j
@Service
public class ChatRedisService {

    private final RedisConfig redisConfig;
    private final RedisTemplate<String, ChatDto> redisTemplate;
    private HashOperations<String, String, String> hashOpsEnterInfo;
    private ListOperations<String, ChatDto> listOpsChatDto;
    private ObjectMapper objectMapper;

    public ChatRedisService(RedisConfig redisConfig, RedisTemplate<String, ChatDto> redisTemplate) {
        this.redisConfig = redisConfig;
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @PostConstruct
    private void init() {
        hashOpsEnterInfo = redisTemplate.opsForHash();
        listOpsChatDto = redisTemplate.opsForList();
    }

    public List<ChatDto> sendChatroomDto(Long roomId, ChatDto lastChat) {
        listOpsChatDto.rightPush(CHAT_ROOM + roomId, lastChat);
        List<ChatDto> chatDtoList = listOpsChatDto.range(CHAT_ROOM + roomId, 0, -1);

        return chatDtoList.stream()
                .map(chat -> {
                    try {
                        if (!chat.getName().startsWith("{")) {
                            // if message null -> create one
                            return ChatDto.builder()
                                    .name(chat.getName())
                                    .nickname(chat.getNickname())
                                    .activeFlag(true)
                                    .build();
                        }
                        return objectMapper.readValue(chat.getName(), ChatDto.class);
                    } catch (JsonProcessingException e) {
                        log.error("Failed to parse chat message from Redis: {}", e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    //사용자 세션으로 입장해있는 roomId 조회
    public String getUserEnterRoomId(String sessionId) {
        return hashOpsEnterInfo.get(ENTER_INFO, sessionId);
    }

    //사용자 세션정보와 맵핑된 roomId 삭제
    public void removeUserEnterInfo(String sessionId) {
        hashOpsEnterInfo.delete(ENTER_INFO, sessionId);
    }

}
