package kr.co.talk.handler;

import java.security.SignatureException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import kr.co.talk.dto.ErrorCode;
import kr.co.talk.dto.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * WebFlux Global Exception Handler
 * 
 * @author shmin
 *
 */
@Slf4j
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        List<Class<? extends Exception>> jwtExceptions = List.of(
                UnsupportedJwtException.class, MalformedJwtException.class,
                SignatureException.class, IllegalArgumentException.class);

        Class<? extends Throwable> exceptionClass = ex.getClass();

        ServerHttpResponse response = exchange.getResponse();

        ErrorDTO errorDTO = new ErrorDTO();

        if (exceptionClass == ExpiredJwtException.class) {
            // token 만료
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            errorDTO.setCode(ErrorCode.EXPIRED.name());
            errorDTO.setMessage(ErrorCode.EXPIRED.getMessage());
        } else if (jwtExceptions.contains(exceptionClass)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            errorDTO.setCode(ErrorCode.INVALID.name());
            errorDTO.setMessage(ErrorCode.INVALID.getMessage());
        } else {
            errorDTO.setCode(ErrorCode.UNKNOWN.name());
            errorDTO.setMessage(ex.getMessage());
        }

        // DataBuffer으로 HTTP 요청 및 응답을 처리함
        DataBuffer buffer = null;
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(errorDTO);
            buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException :: {}", e);
        }

        return response.writeWith(Flux.just(buffer));
    }

}
