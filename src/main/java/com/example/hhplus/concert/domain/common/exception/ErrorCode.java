package com.example.hhplus.concert.domain.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

  String getCode();

  HttpStatus getStatus();

  String getMessage();
}
