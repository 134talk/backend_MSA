package kr.co.talk.global.controller.redis;

import kr.co.talk.global.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RedisController {

    private final RedisService redisService;

    @GetMapping("/redis")
    public String redisHello() {
        redisService.redisString();
        return "redisHello";
    }
}
