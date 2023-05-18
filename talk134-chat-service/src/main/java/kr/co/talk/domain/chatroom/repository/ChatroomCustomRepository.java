package kr.co.talk.domain.chatroom.repository;

import java.util.List;
import kr.co.talk.domain.chatroom.model.Chatroom;

public interface ChatroomCustomRepository {
    public List<Chatroom> findByTeamCode(String teamCode);
}
