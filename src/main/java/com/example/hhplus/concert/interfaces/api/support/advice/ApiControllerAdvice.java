package com.example.hhplus.concert.interfaces.api.support.advice;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.interfaces.api.support.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
class ApiControllerAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(CoreException.class)
  public ResponseEntity<ErrorResponse> handle(CoreException e) {
    switch (e.getErrorType().getLogLevel()) {
      case ERROR -> log.error("Error occurred: {}", e.getMessage(), e);
      case WARN -> log.warn("Warning: {}", e.getMessage());
      default -> log.info("Info: {}", e.getMessage());
    }

    HttpStatus status;
    switch (e.getErrorType().getCode()) {
      case NOT_FOUND -> status = HttpStatus.NOT_FOUND;
      case BAD_REQUEST -> status = HttpStatus.BAD_REQUEST;
      default -> status = HttpStatus.OK;
    }

    return new ResponseEntity<>(
        new ErrorResponse(e.getErrorType().getCode().name(), e.getErrorType().getMessage()),
        status
    );
  }

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    log.error("Unhandled exception occurred", e);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ErrorResponse("500", "에러가 발생했습니다."));
  }
}