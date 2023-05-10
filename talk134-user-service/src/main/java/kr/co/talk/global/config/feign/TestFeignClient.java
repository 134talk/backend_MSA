package kr.co.talk.global.config.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "test", url = "testUrl")
public interface TestFeignClient {
    @GetMapping("/test")
    ResponseEntity getTest();
}
