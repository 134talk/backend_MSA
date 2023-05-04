package kr.co.talk;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController {

    @GetMapping("/user/user")
    public String user() {
        return "user";
    }
}
