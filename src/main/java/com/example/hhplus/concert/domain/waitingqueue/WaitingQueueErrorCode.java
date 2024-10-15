package com.example.hhplus.concert.domain.waitingqueue;

import com.example.hhplus.concert.domain.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum WaitingQueueErrorCode implements ErrorCode {
  WAITING_QUEUE_EXPIRED(HttpStatus.BAD_REQUEST,
      WaitingQueueConstants.WAITING_QUEUE_EXPIRED_MESSAGE);

  private final HttpStatus status;
  private final String message;

  @Override
  public String getCode() {
    return name();
  }

  @Override
  public HttpStatus getStatus() {
    return status;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
