package kr.co.talk.domain.statistics.messagequeue;

import java.time.LocalDateTime;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.talk.global.constants.KafkaConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class StatisticsConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = KafkaConstants.TOPIC_END_CHATTING,
            groupId = KafkaConstants.GROUP_STATISTICS,
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public void endChatting(String kafkaMessage, Acknowledgment ack)
            throws JsonMappingException, JsonProcessingException {
        log.info("Received Msg statistics server, message : {}", kafkaMessage);

        KafkaEndChatroomDTO endChatroomDTO =
                objectMapper.readValue(kafkaMessage, KafkaEndChatroomDTO.class);

        try {
            // TODO MongoDB 저장
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
            // kafka commit
            ack.acknowledge();
        } catch (Exception e) {
            log.error("acknowledge error : {}", e);
        }

    }

    @Builder
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    private static class KafkaEndChatroomDTO {
        private long roomId;
        private LocalDateTime localDateTime;
        // TODO 메시지 보낼게 더 있을지...
    }
}
