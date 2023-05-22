package kr.co.talk.global.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {
    @GetMapping(value = "/user/teamCode/{userId}")
    String getTeamCode(@PathVariable(value = "userId") long userId);
}
