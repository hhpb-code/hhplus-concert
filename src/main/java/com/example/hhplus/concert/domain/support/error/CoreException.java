package com.example.hhplus.concert.domain.support.error;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CoreException extends RuntimeException {

  private final ErrorType errorType;
  private final Object payload;

}
