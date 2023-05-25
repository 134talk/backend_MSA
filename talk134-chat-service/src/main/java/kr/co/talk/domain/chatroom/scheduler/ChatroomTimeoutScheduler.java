package kr.co.talk.domain.chatroom.scheduler;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import kr.co.talk.domain.chatroom.dto.ChatroomNoticeDto;
import kr.co.talk.domain.chatroom.model.Chatroom;
import kr.co.talk.global.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * chatroom의 timeout을 걸고, 대화 마감을 해주기 위한 scheduler
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ChatroomTimeoutScheduler {
    private final RedisService redisService;
    
    private static final long NOTICE_5MINUTE  = 1000 * 60 * 5; 
    
    @Scheduled(fixedRate = 3000)
    public void scheduleNoticeTask() {
        log.debug("fixed rate task - {}", System.currentTimeMillis() / 1000);
        
        // 채팅방 timeout check
        List<ChatroomNoticeDto> chatroomNoticeList = redisService.getChatroomNoticeList();
        
        chatroomNoticeList.forEach(cn->{
           if(System.currentTimeMillis() - cn.getCreateTime() <=  NOTICE_5MINUTE) {
               // TODO 종료 5분전이면 socket으로 알림
               log.info("채팅방 종료 5분전, CHAT ROOM ID ::", cn.getRoomId());
           }else if(System.currentTimeMillis() - cn.getCreateTime() <=0) {
               // TODO 채팅방 종료 알림 SOCKET
               log.info("채팅방 종료 , CHAT ROOM ID ::", cn.getRoomId());
           }
        });
    }
    
}
