package com.example.hhplus.concert.domain.support.error;


import lombok.AllArgsConstructor;
import org.springframework.boot.logging.LogLevel;

@AllArgsConstructor
public enum ErrorType {
  ;

  private final ErrorCode code;
  private final String message;
  private final LogLevel logLevel;

}
