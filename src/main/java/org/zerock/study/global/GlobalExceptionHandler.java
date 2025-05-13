package org.zerock.study.global;


import lombok.Builder;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {
    record ErrorDTO(
            String exception,
            String message
    ) {
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgument(IllegalArgumentException e) {
        log.error(e);
        return ResponseEntity.badRequest()
                .body(new ErrorDTO("IllegalArgumentException", e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeException(RuntimeException e) {
        log.error(e);
        return ResponseEntity.badRequest()
                .body(new ErrorDTO("RuntimeException", e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception e) {
        log.error(e);
        return ResponseEntity.internalServerError()
                .body(new ErrorDTO("Exception", e.getMessage()));

    }
}
