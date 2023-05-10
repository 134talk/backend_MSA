package kr.co.talk.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    private int errorCode;
    private String message;

    public static ErrorDto createErrorDto(CustomError customError){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorCode(customError.getErrorCode());
        errorDto.setMessage(customError.getMessage());
        return errorDto;
    }
}
