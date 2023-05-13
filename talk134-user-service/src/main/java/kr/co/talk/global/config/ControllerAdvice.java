package kr.co.talk.global.config;

import kr.co.talk.global.exception.CustomError;
import kr.co.talk.global.exception.CustomException;
import kr.co.talk.global.exception.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity CustomException(CustomException e){
        return new ResponseEntity(ErrorDto.createErrorDto(e.getCustomError()), HttpStatus.valueOf(e.getCustomError().getStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity commonException(Exception e){
        log.error("",e);
        CustomError serverError = CustomError.SERVER_ERROR;
        return new ResponseEntity(ErrorDto.createErrorDto(serverError), HttpStatus.valueOf(serverError.getStatusCode()));
    }
}
