package kr.co.talk.domain.chatroom.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.talk.domain.chatroom.dto.ChatroomListDto;
import kr.co.talk.domain.chatroom.model.Chatroom;
import kr.co.talk.domain.chatroom.repository.ChatroomRepository;
import kr.co.talk.domain.chatroomusers.entity.ChatroomUsers;
import kr.co.talk.domain.chatroomusers.repository.ChatroomUsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {


    private final ChatroomRepository chatroomRepository;
    private final ChatroomUsersRepository chatroomUsersRepository;

    public List<ChatroomListDto> findChatRooms(long userId,
            String teamCode) {

        List<Chatroom> chatroomEntity = chatroomRepository.findByTeamCode(teamCode);
        return chatroomEntity.stream().map(chatroom -> {
            // TODO emoticon redis에서 조회해서 저장

            List<ChatroomUsers> chatroomUsers = chatroom.getChatroomUsers();
            List<Long> userIds = chatroomUsers.stream().map(ChatroomUsers::getUserId)
                    .collect(Collectors.toList());

            return ChatroomListDto.builder()
                    .roomId(chatroom.getChatroomId())
                    .roomName(chatroom.getName())
                    .userCount(chatroomUsers.size())
                    .joinFlag(userIds.contains(userId))
                    .build();
        }).collect(Collectors.toList());
    }

    @Transactional
    public void createChatroom(String teamCode, List<Long> userList) {
        Chatroom chatroom = Chatroom.builder()
                .name("")
                .teamCode(teamCode)
                .build();


        List<ChatroomUsers> chatroomUsers = userList.stream().map(userId -> {
            return ChatroomUsers.builder()
                    .chatroom(chatroom)
                    .userId(userId)
                    .build();
        }).collect(Collectors.toList());

        // chatroomRepository.save(chatroom);
        chatroomUsersRepository.saveAll(chatroomUsers);
    }
}
