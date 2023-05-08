package kr.co.talk.global.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import kr.co.talk.global.constants.KafkaConstants;
import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class AcknowledgingMessageListenerAdapter implements AcknowledgingMessageListener<String, String> {

	@Override
	@KafkaListener(topics = KafkaConstants.TOPIC_END_CHATTING, groupId = KafkaConstants.GROUP_STATISTICS, containerFactory = "concurrentKafkaListenerContainerFactory")
	public void onMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment) {
		try {
			acknowledgment.acknowledge();
		} catch (Exception e) {
			log.error("consume cause exception : {}", e);
		}
	}

}
