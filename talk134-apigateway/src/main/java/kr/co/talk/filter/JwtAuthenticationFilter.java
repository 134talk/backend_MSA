package kr.co.talk.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import kr.co.talk.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JwtAuthenticationFilter
        extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private final JwtTokenProvider jwtTokenProvider;
    private static final String USER_ID = "userId";

    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        super(Config.class);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();
            if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange);
            }

            String authorizationHeader = headers.get(HttpHeaders.AUTHORIZATION).get(0);

            // 인증
            // Bearer 붙지 않았을 때
            if (!authorizationHeader.startsWith("Bearer ")) {
                return onError(exchange);
            }

            String token = authorizationHeader.split("Bearer ")[1].trim();

            // 토근 유효성 통과 안됐을시 예외 발생, userId 가져옴
            String subject = jwtTokenProvider.getAccessTokenSubject(token);
            log.info("subject:: {} ", subject);

            // header에 userId 추가
            exchange.getRequest().mutate().header(USER_ID, subject); 
            return chain.filter(exchange);
        };
    }


    /**
     * 인증 실패 시, 권한 없음을 나타냄
     * 
     * @param exchange
     * @param errorMsg
     * @param httpStatus
     * @return
     */
    private Mono<Void> onError(ServerWebExchange exchange) {
        log.error("권한 없음");

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        return response.setComplete();

    }



    static class Config {

    }
}
