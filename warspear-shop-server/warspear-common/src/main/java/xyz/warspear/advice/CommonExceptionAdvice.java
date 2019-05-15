package xyz.warspear.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xyz.warspear.exception.WarException;

@ControllerAdvice
public class CommonExceptionAdvice {
    @ExceptionHandler(WarException.class)
    public ResponseEntity<String> handleException(WarException e){
        return ResponseEntity.status(e.getCode()).body(e.getMessage());
    }
}
