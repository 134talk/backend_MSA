package kr.co.talk.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Custom Global Filter
 * 
 * @author shmin
 *
 */
@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    /**
     * Config Class로 Filter를 만들려면 AbstractConfigurable에 Config Class를 등록해야함
     */
    public GlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.debug("Global Filter baseMessage: {}", config.baseMessage);

            // Global preFilter
            if (config.isPreLogger()) {
                log.debug("Global Filter Start : request id = {}", request.getId());
            }

            // Global Post Filter
            // chain.filter가 완료되고 HTTP 응답이 완료된후 HTTP 응답상태 코드 출력
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()) {
                    log.debug("Global Filter End : response statuscode -> {} ",
                            response.getStatusCode());
                }
            }));
        };
    }


    @Data
    static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

}
