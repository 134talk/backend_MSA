package kr.co.talk.domain.user.repository;

import kr.co.talk.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserUid(String userUid);
}
