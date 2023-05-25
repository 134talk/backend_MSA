package kr.co.talk.domain.chatroom.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.talk.domain.chatroom.dto.ChatroomListDto;
import kr.co.talk.domain.chatroom.dto.ChatroomNoticeDto;
import kr.co.talk.domain.chatroom.dto.Emoticons;
import kr.co.talk.domain.chatroom.dto.RoomEmoticon;
import kr.co.talk.domain.chatroom.model.Chatroom;
import kr.co.talk.domain.chatroom.model.EmoticonCode;
import kr.co.talk.domain.chatroom.repository.ChatroomRepository;
import kr.co.talk.domain.chatroom.scheduler.ChatroomTimeoutScheduler;
import kr.co.talk.domain.chatroomusers.entity.ChatroomUsers;
import kr.co.talk.domain.chatroomusers.repository.ChatroomUsersRepository;
import kr.co.talk.global.constants.RedisConstants;
import kr.co.talk.global.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatroomRepository chatroomRepository;
    private final ChatroomUsersRepository chatroomUsersRepository;
    private final RedisService redisService;

    /**
     * 닉네임 또는 이름으로 채팅방 목록 조회
     * 
     * @param userId
     * @param teamCode
     * @param name
     * @return
     */
    public List<ChatroomListDto> findChatRoomsByName(long userId, String teamCode, String name) {
        // TODO User service에서 teamCode와 name으로 검색하려는 userId를 가져옴
        List<Long> findUserIds = new ArrayList<>();
        List<Chatroom> chatroomEntity =
                chatroomRepository.findByTeamCodeAndName(teamCode, findUserIds);
        return convertChatRoomListDto(userId, chatroomEntity);
    }

    /**
     * 전체 채팅방 목록 조회
     * 
     * @param userId
     * @param teamCode
     * @return
     */
    public List<ChatroomListDto> findChatRooms(long userId, String teamCode) {
        List<Chatroom> chatroomEntity = chatroomRepository.findByTeamCode(teamCode);
        return convertChatRoomListDto(userId, chatroomEntity);
    }

    public List<ChatroomListDto> convertChatRoomListDto(long userId,
            List<Chatroom> chatroomEntity) {
        return chatroomEntity.stream().map(chatroom -> {
            // emoticon redis에서 조회해서 저장
            List<RoomEmoticon> emoticonList =
                    redisService.getEmoticonList(chatroom.getChatroomId());

            // EmoticonCode별로 grouping
            Map<EmoticonCode, Integer> sizeByCode = emoticonList.stream()
                    .collect(Collectors.groupingBy(
                            RoomEmoticon::getEmoticonCode,
                            Collectors.collectingAndThen(Collectors.toList(), List::size)));

            // 이모티콘 갯수 top3
            List<Emoticons> emoticons = sizeByCode.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(3)
                    .map(entry -> {
                        return Emoticons.builder()
                                .emoticonCode(entry.getKey())
                                .emoticonCount(entry.getValue())
                                .build();
                    }).collect(Collectors.toList());

            List<ChatroomUsers> chatroomUsers = chatroom.getChatroomUsers();
            List<Long> userIds = chatroomUsers.stream().map(ChatroomUsers::getUserId)
                    .collect(Collectors.toList());

            return ChatroomListDto.builder()
                    .roomId(chatroom.getChatroomId())
                    .roomName(chatroom.getName())
                    .emoticons(emoticons)
                    .chatroomUsers(chatroomUsers)
                    .userCount(chatroomUsers.size())
                    .joinFlag(userIds.contains(userId))
                    .build();
        }).collect(Collectors.toList());
    }

    @Transactional
    public void createChatroom(String teamCode, List<Long> userList) {
        // TODO User service에서 timeout 시간 알아옴
        
        
        Chatroom chatroom = Chatroom.builder()
                .name("")
                .teamCode(teamCode)
                .timeout(0) // TODO timeout
                .build();

        List<ChatroomUsers> chatroomUsers = userList.stream().map(userId -> {
            return ChatroomUsers.builder()
                    .chatroom(chatroom)
                    .userId(userId)
                    .build();
        }).collect(Collectors.toList());

        chatroomUsersRepository.saveAll(chatroomUsers);
        
        
        ChatroomNoticeDto chatroomNoticeDto = ChatroomNoticeDto.builder()
                .roomId(chatroom.getChatroomId())
                .timeout(1000)  // TODO timeout
                .createTime(System.currentTimeMillis())
                .build();
        redisService.pushNoticeList(RedisConstants.ROOM_NOTICE, chatroomNoticeDto);
    }

    @Transactional
    public void updateTimeout(String teamCode, long timeout) {
        chatroomRepository.updateTimeout(teamCode, timeout);
    }
}
