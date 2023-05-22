package kr.co.talk.domain.user.repository;

import kr.co.talk.domain.user.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findTeamByTeamCode(String teamCode);
}
