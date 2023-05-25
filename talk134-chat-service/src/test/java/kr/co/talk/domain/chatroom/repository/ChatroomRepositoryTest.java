package kr.co.talk.domain.chatroom.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import kr.co.talk.domain.chatroom.model.Chatroom;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class ChatroomRepositoryTest {

	@Autowired
	private ChatroomRepository chatroomRepository;
	
	@Test
	void findByTeamCodeAndNameTest() {
		// given
		String teamCode = "test_code2";
		List<Long> userIds = List.of(123456L);
		
		// when
		List<Chatroom> findByTeamCodeAndName = chatroomRepository.findByTeamCodeAndName(teamCode, userIds);
		log.info("findByTeamCodeAndName:::" + findByTeamCodeAndName);
	
		// then
		assertEquals(findByTeamCodeAndName.get(0).getTeamCode(), teamCode);
	}
	
}
