package kr.co.talk.global.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@Slf4j
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    public String bootstrapAddress;

    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); // auto commit false
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // default latest : 가장 최신의
                                                                         // 데이터부터 가져옴

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        // factory.setConcurrency(3); // consumer에서 poll을 하는 thread 수
        factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE); // offset 수동 커밋을 위함
        factory.setCommonErrorHandler(errorHandler());
        return factory;
    }

    
    /**
     * kafka consume이 실패했을 때 default는 최초 요청포햄해서 10회 재시도
     * 모두 실패해면 해당 메시지 skip
     * 커스텀하게 이러한 정책을 운영하기 위한 handler 
     *
     * @return
     */
    public DefaultErrorHandler errorHandler() {
        BackOff backOff = new FixedBackOff(1000, 5); // 1초마다 재시도, 총 5회
        DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, exception) -> {
            log.error("{} consume Failure. cause : {}, message key : {}, message value : {}",
                    consumerRecord.topic(), exception.getMessage(), consumerRecord.key(),
                    consumerRecord.value());
        }, backOff);

        return errorHandler;
    }

}
