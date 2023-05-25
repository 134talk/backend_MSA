package kr.co.talk.global.config.redis;

import kr.co.talk.domain.chatroom.dto.ChatDto;
import kr.co.talk.domain.chatroom.dto.ChatroomNoticeDto;
import kr.co.talk.domain.chatroom.dto.RoomEmoticon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	@Value("${spring.redis.host}")
	public String host;

	@Value("${spring.redis.port}")
	public int port;

	@Value("${spring.redis.password}")
	public String password;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
		redisConfiguration.setHostName(host);
		redisConfiguration.setPort(port);
		redisConfiguration.setPassword(password);

		return new LettuceConnectionFactory(redisConfiguration);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());

		return redisTemplate;
	}

	@Bean
	public StringRedisTemplate stringRedisTemplate() {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
		stringRedisTemplate.setConnectionFactory(redisConnectionFactory());
		stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
		stringRedisTemplate.setValueSerializer(new StringRedisSerializer());

		return stringRedisTemplate;
	}

	@Bean
	public RedisTemplate<String, ChatDto> chatDtoRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, ChatDto> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatDto.class));
		return redisTemplate;
	}
	
	@Bean
	public RedisTemplate<String, ChatroomNoticeDto> chatroomNoticeDtoRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
	    RedisTemplate<String, ChatroomNoticeDto> redisTemplate = new RedisTemplate<>();
	    redisTemplate.setConnectionFactory(redisConnectionFactory);
	    redisTemplate.setKeySerializer(new StringRedisSerializer());
	    redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatroomNoticeDto.class));
	    return redisTemplate;
	}
	
}
