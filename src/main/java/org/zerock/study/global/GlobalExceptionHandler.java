package org.zerock.study.global;


import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    record ErrorDTO(
            String exception,
            String message
    ){}

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgument(IllegalArgumentException e){

        return ResponseEntity.badRequest()
                .body(new ErrorDTO("IllegalArgumentException", e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception e){
        return ResponseEntity.internalServerError()
                .body(new ErrorDTO("Exception", e.getMessage()));

    }
}
