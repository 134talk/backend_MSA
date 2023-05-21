package kr.co.talk.domain.chatroom.service;

import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.talk.domain.chatroom.dto.ChatroomListDto;
import kr.co.talk.domain.chatroom.dto.Emoticons;
import kr.co.talk.domain.chatroom.dto.RoomEmoticon;
import kr.co.talk.domain.chatroom.model.Chatroom;
import kr.co.talk.domain.chatroom.model.EmoticonCode;

import static kr.co.talk.domain.chatroom.model.QChatroom.chatroom;
import static kr.co.talk.domain.chatroomusers.entity.QChatroomUsers.chatroomUsers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import kr.co.talk.domain.chatroomusers.entity.ChatroomUsers;
import kr.co.talk.global.constants.RedisConstants;
import kr.co.talk.global.service.redis.RedisService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Transactional(readOnly = true)
public class ChatRoomServiceTest {
	@Autowired
	private ChatRoomService chatRoomService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private JPAQueryFactory jpaQueryFactory;

	private List<Long> userList;
	long user1 = 14212312L;
	long user2 = 24124124L;
	long user3 = 234324123L;

	@BeforeEach
	void setup() {
		userList = List.of(user1, user2, user3);
	}

	@Test
	@DisplayName("채팅방 잘 만들어지는지 확인")
	@Transactional
	@Rollback(false)
	public void createChatroom() {
		// given
		String teamCode = "teamCode_test";
		chatRoomService.createChatroom(teamCode, userList);

		// when
		Chatroom firstChatroom = jpaQueryFactory.selectFrom(chatroom).leftJoin(chatroom.chatroomUsers, chatroomUsers)
				.fetchJoin().where(chatroom.teamCode.eq(teamCode)).fetchFirst();

		List<ChatroomUsers> findChatroomUsers = jpaQueryFactory.selectFrom(chatroomUsers)
				.where(chatroomUsers.chatroom.eq(firstChatroom)).fetch();

		// then
		assertNotNull(firstChatroom);
		assertEquals(findChatroomUsers.size(), userList.size());

	}

	@Test
	@Transactional
	@Rollback(false)
	@DisplayName("대화방 목록 조회 api")
	public void findChatRoomsTest() {
		// given
		long userId = 2L;
		String teamCode = "teamCode_test";

		// redis에 이모티콘 저장
		RoomEmoticon roomEmoticon1 = RoomEmoticon.builder().emoticonCode(EmoticonCode.EMOTICON_TP1).fromUserId(user1)
				.toUserId(user2).roomId(1L).build();

		RoomEmoticon roomEmoticon2 = RoomEmoticon.builder().emoticonCode(EmoticonCode.EMOTICON_TP1).fromUserId(user1)
				.toUserId(user3).roomId(1L).build();

		RoomEmoticon roomEmoticon3 = RoomEmoticon.builder().emoticonCode(EmoticonCode.EMOTICON_TP2).fromUserId(user2)
				.toUserId(user3).roomId(1L).build();

		RoomEmoticon roomEmoticon4 = RoomEmoticon.builder().emoticonCode(EmoticonCode.EMOTICON_TP2).fromUserId(user1)
				.toUserId(user1).roomId(3424L).build();

		RoomEmoticon roomEmoticon5 = RoomEmoticon.builder().emoticonCode(EmoticonCode.EMOTICON_TP4).fromUserId(user2)
				.toUserId(user3).roomId(1L).build();

		redisService.pushList(1L + RedisConstants.ROOM_EMOTICON, roomEmoticon1);
		redisService.pushList(1L + RedisConstants.ROOM_EMOTICON, roomEmoticon2);
		redisService.pushList(1L + RedisConstants.ROOM_EMOTICON, roomEmoticon3);
		redisService.pushList(1L + RedisConstants.ROOM_EMOTICON, roomEmoticon4);
		redisService.pushList(1L + RedisConstants.ROOM_EMOTICON, roomEmoticon5);

		// when
		List<ChatroomListDto> findChatRooms = chatRoomService.findChatRooms(userId, teamCode);

		log.info("findChatRooms::::::" + findChatRooms);
		// then
		assertEquals(findChatRooms.get(0).getEmoticons().size(), 3);
		assertTrue(findChatRooms.get(0).isJoinFlag());
	}

}
