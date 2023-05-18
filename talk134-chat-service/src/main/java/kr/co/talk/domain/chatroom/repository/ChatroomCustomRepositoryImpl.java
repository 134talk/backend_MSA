package kr.co.talk.domain.chatroom.repository;

import java.util.List;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.talk.domain.chatroom.model.Chatroom;
import static kr.co.talk.domain.chatroomusers.entity.QChatroomUsers.chatroomUsers;
import static kr.co.talk.domain.chatroom.model.QChatroom.chatroom;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChatroomCustomRepositoryImpl implements ChatroomCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Chatroom> findByTeamCode(String teamCode) {
        return jpaQueryFactory.selectFrom(chatroom)
                .distinct()
                .leftJoin(chatroom.chatroomUsers, chatroomUsers)
                .fetchJoin()
                .where(chatroom.teamCode.eq(teamCode))
                .fetch();

        // List<ChatroomListDto> collect = jpaQueryFactory
        // .selectFrom(chatroom)
        // .innerJoin(chatroom.chatroomUsers, chatroomUsers)
        // .transform(groupBy(chatroom)
        // .as(new QChatroomListDto(chatroom.chatroomId, list(chatroomUsers))))
        // .values().stream().map(dto -> {
        // List<ChatroomUsers> chatroomUsers = dto.getChatroomUsers();
        // List<Long> userIds = chatroomUsers.stream().map(ChatroomUsers::getUserId)
        // .collect(Collectors.toList());
        // dto.setUserCount(chatroomUsers.size());
        // dto.setJoinFlag(userIds.contains(userId));
        // return dto;
        // }).collect(Collectors.toList());
    }
}
