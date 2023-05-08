package kr.co.talk.global.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	@Value(value = "${spring.kafka.bootstrap-servers}")
	public String bootstrapAddress;

	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); // auto commit false
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // default latest : 가장 최신의 데이터부터 가져옴

		return new DefaultKafkaConsumerFactory<>(config);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setConcurrency(3); // consumer에서 poll을 하는 thread 수
		factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE); // offset 수동 커밋을 위함
		factory.getContainerProperties().setMessageListener(acknowledgingMessageListenerAdapter());
		return factory;
	}

	public AcknowledgingMessageListenerAdapter acknowledgingMessageListenerAdapter() {
		return new AcknowledgingMessageListenerAdapter();
	}
}
