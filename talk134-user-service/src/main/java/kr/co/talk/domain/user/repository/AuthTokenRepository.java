package kr.co.talk.domain.user.repository;

import kr.co.talk.domain.user.model.AuthToken;
import org.springframework.data.repository.CrudRepository;

public interface AuthTokenRepository extends CrudRepository<AuthToken,String> {
    AuthToken findByUserId(Long userId);
}
