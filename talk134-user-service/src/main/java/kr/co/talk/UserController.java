package kr.co.talk;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(path="user")
public class UserController {

    @GetMapping("/user")
    public String user() {
        return "user";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
