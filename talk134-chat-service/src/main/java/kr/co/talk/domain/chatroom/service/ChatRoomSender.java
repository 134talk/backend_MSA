package kr.co.talk.domain.chatroom.service;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import kr.co.talk.domain.chatroom.dto.ChatDto;
import kr.co.talk.domain.chatroom.dto.ChatroomSendDto;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.talk.global.constants.KafkaConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomSender {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final ChatRedisService chatRedisService;

    public void sendEndChatting(long roomId) throws JsonProcessingException {
        KafkaEndChatroomDTO chatroomDTO = KafkaEndChatroomDTO.builder()
                .roomId(roomId)
                .localDateTime(LocalDateTime.now())
                .build();

        String jsonInString = objectMapper.writeValueAsString(chatroomDTO);

        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(KafkaConstants.TOPIC_END_CHATTING,
                        jsonInString);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                log.info("sent topic=[{}] roodId [{}] with offset=[{}]", recordMetadata.topic(),
                        roomId,
                        recordMetadata.offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                // TODO 실패 처리
                log.info("unable to send message roomId=[{}] due to : {}", roomId, ex.getMessage());
            }
        });
    }

    public void test_send() {
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send("test_topic", "test1");
//        ListenableFuture<SendResult<String, String>> future =
//                kafkaTemplate.send("test_topic", "key1", "test1"); // partition 지정
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                log.info("recordMetadata :: " + recordMetadata);
                log.info("partition:::" + recordMetadata.partition());
                log.info("offset:::" + recordMetadata.offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                // TODO Auto-generated method stub

            }

        });
    }

    // Kafka Producer
    public List<ChatDto> sendMessageToKafka(ChatroomSendDto sendDto) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper()
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        String jsonInString = objectMapper.writeValueAsString(sendDto);

        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(KafkaConstants.TOPIC_CHAT, jsonInString);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                log.info("sent topic=[{}] roodId [{}] with offset=[{}]", recordMetadata.topic(),
                        sendDto.getRoomId(),
                        recordMetadata.offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                // TODO 실패 처리
                log.info("unable to send message roomId=[{}] due to : {}", sendDto.getRoomId(), ex.getMessage());
            }
        });

        return chatRedisService.sendChatroomDto(sendDto.getRoomId(), ChatDto.builder().name("name").nickname("nickname").activeFlag(true).build());
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
