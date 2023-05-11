package kr.co.talk.global.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {
    @GetMapping(value = "/user/user")
    String getUser();
}
