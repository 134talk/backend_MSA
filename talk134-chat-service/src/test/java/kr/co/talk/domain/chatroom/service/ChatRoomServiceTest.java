package kr.co.talk.domain.chatroom.service;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.talk.domain.chatroom.dto.ChatroomListDto;
import kr.co.talk.domain.chatroom.model.Chatroom;
import static kr.co.talk.domain.chatroom.model.QChatroom.chatroom;
import static kr.co.talk.domain.chatroomusers.entity.QChatroomUsers.chatroomUsers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import kr.co.talk.domain.chatroomusers.entity.ChatroomUsers;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Transactional(readOnly = true)
public class ChatRoomServiceTest {
    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Test
    @DisplayName("채팅방 잘 만들어지는지 확인")
    @Transactional
    @Rollback(false)
    public void createChatroom() {
        // given
        String teamCode = "teamCode_test";
        List<Long> userList = List.of(1L, 2L, 5L);
        chatRoomService.createChatroom(teamCode, userList);

        // when
        Chatroom firstChatroom = jpaQueryFactory.selectFrom(chatroom)
                .leftJoin(chatroom.chatroomUsers, chatroomUsers)
                .fetchJoin()
                .where(chatroom.teamCode.eq(teamCode))
                .fetchFirst();


        List<ChatroomUsers> findChatroomUsers = jpaQueryFactory.selectFrom(chatroomUsers)
                .where(chatroomUsers.chatroom.eq(firstChatroom))
                .fetch();

        // then
        assertNotNull(firstChatroom);
        assertEquals(findChatroomUsers.size(), userList.size());



    }

    @Test
    @Transactional
    @Rollback(false)
    public void convertChatroomListTest() {
        // given
        long userId = 2L;
        String teamCode = "teamCode_test";
        List<Long> userList = List.of(1L, 2L, 5L);

        // chatRoomService.createChatroom(teamCode, userList);

        // when
        List<ChatroomListDto> findChatRooms =
                chatRoomService.findChatRooms(userId, teamCode);

        // then
        assertEquals(findChatRooms.size(), 1);
        assertEquals(findChatRooms.get(0).getUserCount(), 3);
        assertTrue(findChatRooms.get(0).isJoinFlag());
    }

}
