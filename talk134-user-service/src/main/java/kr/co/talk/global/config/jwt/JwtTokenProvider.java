package kr.co.talk.global.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kr.co.talk.global.exception.CustomException;
import kr.co.talk.global.exception.CustomError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.accessToken.secretKey}")
    private String accessTokenKey;

    @Value("${jwt.refreshToken.secretKey}")
    private String refreshTokenKey;

    private long tokenValidTime = 1000L * 60 * 30; // 30분

    private long refreshTokenValidTime = 1000L * 60 * 60 * 24 * 15; // 15일

    // 액세스 토큰 생성
    public String createAccessToken(String userId) {

        Claims claims = Jwts.claims().setSubject(userId);
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(date) // 토큰 발행일자
                .setExpiration(new Date(date.getTime() + tokenValidTime))
                .signWith(Keys.hmacShaKeyFor(accessTokenKey.getBytes(StandardCharsets.UTF_8)),SignatureAlgorithm.HS256)
                .compact();
    }

    // 리프레시 토큰 생성
    public String createRefreshToken() {
        String uuid = UUID.randomUUID().toString();
        Claims claims = Jwts.claims().setSubject(uuid);
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(date) // 토큰 발행일자
                .setExpiration(new Date(date.getTime() + refreshTokenValidTime))
                .signWith(Keys.hmacShaKeyFor(refreshTokenKey.getBytes(StandardCharsets.UTF_8)),SignatureAlgorithm.HS256)
                .compact();
    }

    //subject 값 조회
    public String getAccessTokenSubject(String token) {
        return parseClaims(token, accessTokenKey).getSubject();
    }


    //accessToken 유효성 체크
    public void validAccessToken(String token) {
        try {
            parseClaims(token, accessTokenKey);
        }
        catch (ExpiredJwtException e){
            log.error("",e);
            throw new CustomException(CustomError.ACCESS_TOKEN_EXPIRED);
        }
        catch (Exception e) {
            log.error("",e);
            throw new CustomException(CustomError.ACCESS_TOKEN_INVALID);
        }
    }

    //refreshToken 유효성 체크
    public void validRefreshToken(String refreshToken) {
        try {
            parseClaims(refreshToken, refreshTokenKey);
        }
        catch (ExpiredJwtException e){
            log.error("",e);
            throw new CustomException(CustomError.REFRESH_TOKEN_EXPIRED);
        }
        catch (Exception e) {
            log.error("",e);
            throw new CustomException(CustomError.REFRESH_TOKEN_INVALID);
        }
    }

    private Claims parseClaims(String token, String secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
