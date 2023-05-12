package kr.co.talk.domain.user.service;

import kr.co.talk.domain.user.dto.SocialKakaoDto;
import kr.co.talk.domain.user.model.User;
import kr.co.talk.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User createUser(SocialKakaoDto.UserInfo userInfoDto) {
        User user = userInfoDto.createUser();
        return userRepository.save(user);
    }

    public User findByUserUid(String userUid) {
        return userRepository.findByUserUid(userUid);
    }

}
