package kr.co.talk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    EXPIRED("Access Token is Expired!"), INVALID("Invalid Access Token!"), UNKNOWN("UNKOWN");

    private String message;
}
