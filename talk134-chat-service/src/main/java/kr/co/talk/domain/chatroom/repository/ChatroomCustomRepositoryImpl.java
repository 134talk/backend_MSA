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
    }

	@Override
	public List<Chatroom> findByTeamCodeAndName(String teamCode, List<Long> userIds) {
		return jpaQueryFactory.selectFrom(chatroom)
				.leftJoin(chatroom.chatroomUsers, chatroomUsers)
				.fetchJoin()
				.where(chatroom.teamCode.eq(teamCode), 
						chatroomUsers.userId.in(userIds))
				.fetch();
	}

}
