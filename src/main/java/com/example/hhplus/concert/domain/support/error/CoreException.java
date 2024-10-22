package com.example.hhplus.concert.domain.support.error;

import lombok.Getter;

@Getter
public class CoreException extends RuntimeException {

  private final IErrorType errorType;
  private final Object data;

  public CoreException(IErrorType errorType, Object data) {
    super(errorType.getMessage());
    this.errorType = errorType;
    this.data = data;
  }

  public CoreException(IErrorType errorType) {
    this(errorType, null);
  }
}
