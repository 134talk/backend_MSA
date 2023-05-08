package kr.co.talk.domain.chatroom.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import kr.co.talk.global.constants.KafkaConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomSender {
	private final KafkaTemplate<String, String> kafkaTemplate;

	public void sendEndChatting(String roomId) {
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(KafkaConstants.TOPIC_END_CHATTING,
				roomId);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				RecordMetadata recordMetadata = result.getRecordMetadata();
				log.info("sent topic=[{}] roodId [{}] with offset=[{}]", recordMetadata.topic(), roomId,
						recordMetadata.offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				// TODO 실패 처리
				log.info("unable to send message roomId=[{}] due to : {}", roomId, ex.getMessage());
			}
		});
	}

}
