package xyz.warspear.advice;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.warspear.dto.CommonResponseEntity;
import xyz.warspear.exception.WarException;

@ControllerAdvice
public class CommonExceptionAdvice {
    @ExceptionHandler(WarException.class)
    public ResponseEntity<CommonResponseEntity<String>> handleException(WarException e){
        return ResponseEntity.status(e.getCode()).body(new CommonResponseEntity<>(e));
    }
}
