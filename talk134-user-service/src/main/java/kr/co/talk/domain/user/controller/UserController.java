package kr.co.talk.domain.user.controller;

import kr.co.talk.domain.user.dto.RegisterAdminUserDto;
import kr.co.talk.domain.user.dto.RegisterUserDto;
import kr.co.talk.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/admin/register")
    public String registerAdminUser(@RequestBody RegisterAdminUserDto registerAdminUserDto, @RequestHeader("Authorization") String accessToken) {
        String teamCode = userService.registerAdminUser(registerAdminUserDto, accessToken);
        return teamCode;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody RegisterUserDto registerUserDto, @RequestHeader("Authorization") String accessToken) {
        userService.registerUser(registerUserDto, accessToken);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/name")
    public String findUserName(@RequestHeader("Authorization") String accessToken) {
        String name = userService.nameFromUser(accessToken);
        return name;
    }

    @GetMapping("/teamCode/{userId}")
    public String getUserTeamCode(@RequestHeader("Authorization") String accessToken, @PathVariable Long userId) {
        String teamCode = userService.findTeamCode(accessToken, userId);
        return teamCode;
    }

}

