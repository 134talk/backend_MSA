package kr.co.talk.global.exception;

import org.springframework.http.HttpStatus;

public enum CustomError {

    //인증
    ACCESS_TOKEN_INVALID(1000,"유효하지 않은 인증 토큰입니다.", HttpStatus.UNAUTHORIZED.value()),
    ACCESS_TOKEN_EXPIRED(1001,"인증 토큰이 만료되었습니다",HttpStatus.UNAUTHORIZED.value()),
    REFRESH_TOKEN_INVALID(1010,"유효하지 않은 재발급 토큰입니다.",HttpStatus.UNAUTHORIZED.value()),
    REFRESH_TOKEN_EXPIRED(1011,"재발급 토큰이 만료되었습니다",HttpStatus.UNAUTHORIZED.value()),
    AUTH_TOKEN_CREATE_FAIL(1020,"토큰 발급에 실패했습니다.",HttpStatus.BAD_REQUEST.value()),

    //공통
    SERVER_ERROR(3000,"알수 없는 문제가 발생했습니다.",HttpStatus.INTERNAL_SERVER_ERROR.value());

    private int errorCode;
    private String message;
    private int statusCode;


    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }


    CustomError(int errorCode, String message, int statusCode) {
        this.errorCode = errorCode;
        this.message = message;
        this.statusCode = statusCode;
    }

}
