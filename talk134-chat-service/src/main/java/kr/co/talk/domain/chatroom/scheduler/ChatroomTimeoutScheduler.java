package kr.co.talk.domain.chatroom.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import kr.co.talk.domain.chatroom.model.Chatroom;
import lombok.extern.slf4j.Slf4j;

/**
 * chatroom의 timeout을 걸고, 대화 마감을 해주기 위한 scheduler
 */
@Slf4j
public class ChatroomTimeoutScheduler {
    private static final ScheduledExecutorService scheduledTheadpool =
            Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    public static void noticeChatroom(Chatroom chatroom) {
        scheduledTheadpool.schedule(() -> {
            log.info("roomId :: {} , 5분 남았습니다", chatroom.getChatroomId());
            scheduledTheadpool.schedule(() -> {
                // 5분뒤에 채팅방 종료시킴
                endChatroom(chatroom);
            }, 1000 * 60 * 5, TimeUnit.MILLISECONDS);
        }, chatroom.getTimeout(), TimeUnit.MILLISECONDS);
    }

    private static void endChatroom(Chatroom chatroom) {
        log.info("roomId :: {} ,  대화 종료 시킵니다.", chatroom.getChatroomId());
    }
}
