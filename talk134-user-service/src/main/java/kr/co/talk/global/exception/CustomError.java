package kr.co.talk.global.exception;

import org.springframework.http.HttpStatus;

public enum CustomError {

    //인증
    ACCESS_TOKEN_INVALID(1000,"유효하지 않은 인증 토큰입니다.", HttpStatus.UNAUTHORIZED.value()),
    ACCESS_TOKEN_EXPIRED(1001,"인증 토큰이 만료되었습니다",HttpStatus.UNAUTHORIZED.value()),
    REFRESH_TOKEN_INVALID(1010,"유효하지 않은 재발급 토큰입니다.",HttpStatus.UNAUTHORIZED.value()),
    REFRESH_TOKEN_EXPIRED(1011,"재발급 토큰이 만료되었습니다",HttpStatus.UNAUTHORIZED.value()),
    AUTH_TOKEN_CREATE_FAIL(1020,"토큰 발급에 실패했습니다.",HttpStatus.BAD_REQUEST.value()),

    //회원 등록
    TEAM_CODE_NOT_FOUND(1030, "등록되지 않은 팀코드입니다.", HttpStatus.NOT_FOUND.value()),
    ADMIN_ALREADY_EXISTS(1031, "관리자로 가입한 이력이 있습니다.", HttpStatus.FORBIDDEN.value()),
    ADMIN_TEAM_ALREADY_EXISTS(1032, "팀 내에 관리자가 이미 존재합니다.", HttpStatus.FORBIDDEN.value()),
    ADMIN_CANNOT_REGISTER_USER(1033, "관리자로 가입한 이력이 존재하여 일반 회원으로 등록하실 수 없습니다.", HttpStatus.FORBIDDEN.value()),
    USER_ALREADY_EXISTS(1034, "가입한 이력이 있습니다.", HttpStatus.FORBIDDEN.value()),
    USER_DOES_NOT_EXIST(1035, "해당 사용자가 존재하지 않습니다.", HttpStatus.NOT_FOUND.value()),
    TOKEN_DOES_NOT_MATCH(1036, "사용자 아이디와 인증 토큰이 매치하지 않습니다.", HttpStatus.UNAUTHORIZED.value()),

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
