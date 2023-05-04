package kr.co.talk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import kr.co.talk.handler.GlobalExceptionHandler;

@SpringBootApplication
public class Talk134ApigatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(Talk134ApigatewayApplication.class, args);
    }

    @Bean
    public ErrorWebExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }


}
