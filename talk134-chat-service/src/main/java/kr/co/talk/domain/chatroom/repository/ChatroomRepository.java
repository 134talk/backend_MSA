package kr.co.talk.domain.chatroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import kr.co.talk.domain.chatroom.model.Chatroom;

public interface ChatroomRepository
        extends JpaRepository<Chatroom, Long>, ChatroomCustomRepository {
    @Modifying(clearAutomatically = true)
    @Query("update Chatroom c set c.timeout = :timeout where teamCode = :teamCode")
    public int updateTimeout(@Param("teamCode") String teamCode, @Param(value = "timeout") long timeout);
}
