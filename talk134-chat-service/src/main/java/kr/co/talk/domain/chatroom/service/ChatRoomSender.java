package kr.co.talk.domain.chatroom.service;

import java.time.LocalDateTime;
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
