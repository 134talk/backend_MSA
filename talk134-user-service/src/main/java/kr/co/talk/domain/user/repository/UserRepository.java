package kr.co.talk.domain.user.repository;

import kr.co.talk.domain.user.model.Team;
import kr.co.talk.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserUid(String userUid);
    User findByUserId(Long id);
    Optional<User> findUserByRoleAndTeam(String role, Team team);
    Optional<User> findUserByUserName(String name);
}
