package kr.co.talk.domain.chatroomusers.repository;

import org.springframework.data.repository.CrudRepository;
import kr.co.talk.domain.chatroomusers.entity.ChatroomUsers;

public interface ChatroomUsersRepository extends CrudRepository<ChatroomUsers, Long> {

}
