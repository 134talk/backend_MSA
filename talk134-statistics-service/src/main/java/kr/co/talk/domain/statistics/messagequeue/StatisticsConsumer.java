package kr.co.talk.domain.statistics.messagequeue;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.talk.global.constants.KafkaConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class StatisticsConsumer {

	private final ObjectMapper objectMapper;

	@KafkaListener(topics = KafkaConstants.TOPIC_END_CHATTING, groupId = KafkaConstants.GROUP_STATISTICS)
	public void endChatting(String kafkaMessage, Acknowledgment ack) {
		log.info("Received Msg statistics server, message : {}", kafkaMessage);

		// TODO MongoDB 저장
	}
}
