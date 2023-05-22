package kr.co.talk.domain.chatroom.scheduler;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import kr.co.talk.domain.chatroom.model.Chatroom;
import kr.co.talk.domain.chatroom.repository.ChatroomRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ChatroomTimeoutSchedulerTest {

    @Autowired
    private ChatroomRepository chatroomRepository;

    @Test
    @DisplayName("채팅방 마감 알림 테스트")
    public void noticeChatroomTest() throws InterruptedException {
        List<Chatroom> chatroomList = chatroomRepository.findByTeamCode("test_code2");
        chatroomList.forEach(cr -> ChatroomTimeoutScheduler.noticeChatroom(cr));
        
        Thread.sleep(10000);
    }

}
