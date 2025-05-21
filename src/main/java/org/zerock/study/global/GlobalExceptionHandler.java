package org.zerock.study.global;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

    record ErrorDTO(String exception, String message) {
    }

    // 공통 예외 응답 생성 메서드
    private ResponseEntity<ErrorDTO> buildErrorResponse(String type, Exception e, HttpStatus status) {
        // 예외 로그 출력 (스택 트레이스 포함)
        log.error("Exception handled: {}", type, e);

        // 예외 정보를 포함한 응답 본문 생성
        return ResponseEntity.status(status).body(new ErrorDTO(type, e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgument(IllegalArgumentException e) {
        return buildErrorResponse("IllegalArgumentException", e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeException(RuntimeException e) {
        return buildErrorResponse("RuntimeException", e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception e) {
        return buildErrorResponse("Exception", e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
