package kr.co.talk.domain.user.repository;

import kr.co.talk.domain.user.model.Team;
import kr.co.talk.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserUid(String userUid);
    User findByUserId(Long id);
    Optional<User> findUserByRoleAndTeam(String role, Team team);
    @Query("SELECT u FROM User u WHERE u.userName = :searchName OR u.nickname = :searchName")
    List<User> findUserByUserNameOrNickname(@Param("searchName") String searchName);
}
