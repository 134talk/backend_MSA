package kr.co.talk.global.config.kafka;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;
/**
 * kafka producer config
 * 
 * @author shmin
 *
 */
@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

	@Value(value = "${spring.kafka.bootstrap-servers}")
	public String bootstrapAddress;

	@Bean
	public ProducerFactory<String, String> senderProps() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		// Kafka 브로커로 메시지를 보낼때 직렬화/역직렬화 방식
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.RETRIES_CONFIG, 3); // 전송 실패시 재시도 횟수
		// ackmode
		// 0 : 자신이 보낸 메시지에 대해 카프카로부터 확인을 기다리지 않음
		// 1 : 자신이 보낸 메시지에 대해 카프카의 leader가 메시지를 받았는지 기다림, follower들은 확인하지 않음
		// all(-1) : 카프카의 leader와 follwer까지 받았는지 확인. 손실될 확률 거의 없음
		props.put(ProducerConfig.ACKS_CONFIG, "all"); 
		return new DefaultKafkaProducerFactory<>(props);
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(senderProps());
	}

}
