package kr.co.talk.domain.user.controller;

import kr.co.talk.domain.user.dto.RegisterAdminUserDto;
import kr.co.talk.domain.user.dto.RegisterUserDto;
import kr.co.talk.domain.user.dto.ResponseDto;
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
    public ResponseDto.TeamCodeResponseDto registerAdminUser(@RequestBody RegisterAdminUserDto registerAdminUserDto, @RequestHeader(value = "userId") Long userId) {
        return userService.registerAdminUser(registerAdminUserDto, userId);
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody RegisterUserDto registerUserDto, @RequestHeader(value = "userId") Long userId) {
        userService.registerUser(registerUserDto, userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseDto.NameResponseDto findUserName(@RequestHeader(value = "userId") Long userId) {
        return userService.nameFromUser(userId);
    }

    @GetMapping("/teamCode/{userId}")
    public ResponseDto.TeamCodeResponseDto getUserTeamCode(@PathVariable("userId") Long userId) {
        return userService.findTeamCode(userId);
    }

    @GetMapping("/id/{searchName}")
    public ResponseDto.UserIdResponseDto getUserIdByName (@PathVariable("searchName") String searchName) {
        return userService.searchUserId(searchName);
    }

}

