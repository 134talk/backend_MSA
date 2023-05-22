package kr.co.talk.domain.user.service;

import kr.co.talk.domain.user.dto.RegisterAdminUserDto;
import kr.co.talk.domain.user.dto.RegisterUserDto;
import kr.co.talk.domain.user.dto.ResponseDto;
import kr.co.talk.domain.user.dto.SocialKakaoDto;
import kr.co.talk.domain.user.model.Team;
import kr.co.talk.domain.user.model.User;
import kr.co.talk.domain.user.repository.TeamRepository;
import kr.co.talk.domain.user.repository.UserRepository;
import kr.co.talk.global.config.jwt.JwtTokenProvider;
import kr.co.talk.global.exception.CustomError;
import kr.co.talk.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public User createUser(SocialKakaoDto.UserInfo userInfoDto) {
        User user = userInfoDto.createUser();
        return userRepository.save(user);
    }

    @Transactional
    public User createAdminUser(Long userId) {
        User user = User.builder().userId(userId).role("ROLE_ADMIN").build();
        return userRepository.save(user);
    }

    @Transactional
    public User createRoleUser(Long userId) {
        User user = User.builder().userId(userId).role("ROLE_USER").build();
        return userRepository.save(user);
    }

    public User findByUserUid(String userUid) {
        return userRepository.findByUserUid(userUid);
    }

    @Transactional
    public String saveTeam(RegisterAdminUserDto registerAdminUserDto) {
        String teamCode = makeCode();
        Team team = Team.builder().teamName(registerAdminUserDto.getTeamName()).teamCode(teamCode).build();
        teamRepository.save(team);
        log.info("TEAM = {}", team.getTeamName());
        log.info("TEAM = {}", team.getId());
        return teamCode;
    }

    public Long subjectFromToken(String accessToken) {
        jwtTokenProvider.validAccessToken(accessToken);
        Long userId = Long.valueOf(jwtTokenProvider.getAccessTokenSubject(accessToken));
        return userId;
    }

    public ResponseDto.NameResponseDto nameFromUser(String accessToken) {
        User user = userRepository.findByUserId(subjectFromToken(accessToken));
        if (user == null) {
            throw new CustomException(CustomError.USER_DOES_NOT_EXIST);
        } else {
            ResponseDto.NameResponseDto nameResponseDto = new ResponseDto.NameResponseDto();
            nameResponseDto.setName(user.getUserName());
            return nameResponseDto;
        }
    }

    public ResponseDto.TeamCodeResponseDto findTeamCode(String accessToken, Long userId) {
        Long subjectId = subjectFromToken(accessToken);
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return null;
        } if (!subjectId.equals(userId)) {
            throw new CustomException(CustomError.TOKEN_DOES_NOT_MATCH);
        } else {
            ResponseDto.TeamCodeResponseDto teamCodeResponseDto = new ResponseDto.TeamCodeResponseDto();
            teamCodeResponseDto.setTeamCode(user.getTeam().getTeamCode());
            return teamCodeResponseDto;
        }
    }

    public ResponseDto.TeamCodeResponseDto registerAdminUser(RegisterAdminUserDto registerAdminUserDto, String accessToken) {
        Long userId = subjectFromToken(accessToken);
        log.info("userId === {}", userId);

        String teamCode = saveTeam(registerAdminUserDto);
        Team team = teamRepository.findTeamByTeamCode(teamCode);

        Optional<User> existingAdmin = userRepository.findUserByRoleAndTeam("ROLE_ADMIN", team);
        if (existingAdmin.isPresent()) {
            throw new CustomException(CustomError.ADMIN_TEAM_ALREADY_EXISTS);
        }

        User user = userRepository.findByUserId(userId);
        if (user == null) {
            // 사용자 정보가 없을 경우, 새로운 사용자 생성
            user = createAdminUser(userId);
        } else if (user.getRole().equals("ROLE_ADMIN")) {
            throw new CustomException(CustomError.ADMIN_ALREADY_EXISTS);
        }

        user.registerInfo(registerAdminUserDto.getName(), team, "ROLE_ADMIN");
        userRepository.save(user);

        log.info("TEAM = {}", team.getTeamName());
        log.info("TEAM = {}", team.getId());
        log.info("USER = {}", user.getRole());

        ResponseDto.TeamCodeResponseDto teamCodeResponseDto = new ResponseDto.TeamCodeResponseDto();
        teamCodeResponseDto.setTeamCode(teamCode);
        return teamCodeResponseDto;
    }

    private static String makeCode() {
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            codeBuilder.append(randomChar);
        }

        String code = codeBuilder.toString();
        log.info("code -> {}", code);
        return code;
    }

    public void registerUser(RegisterUserDto registerUserDto, String accessToken) {
        Long userId = subjectFromToken(accessToken);
        log.info("userId === {}", userId);

        Team team = teamRepository.findTeamByTeamCode(registerUserDto.getTeamCode());
        if (team == null) {
            throw new CustomException(CustomError.TEAM_CODE_NOT_FOUND);
        }
        log.info("TEAM = {}", team.getTeamName());
        log.info("TEAM = {}", team.getTeamCode());
        log.info("TEAM = {}", team.getId());
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            // 사용자 정보가 없을 경우, 새로운 사용자 생성
            user = createRoleUser(userId);
        } else {
            if (user.getRole().equals("ROLE_ADMIN")) {
                throw new CustomException(CustomError.ADMIN_CANNOT_REGISTER_USER);
            }
            throw new CustomException(CustomError.USER_ALREADY_EXISTS);
        }
        user.registerInfo(registerUserDto.getName(), team, "ROLE_USER");
        userRepository.save(user);
    }

}
