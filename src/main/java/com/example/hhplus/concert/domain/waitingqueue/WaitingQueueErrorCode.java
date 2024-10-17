package com.example.hhplus.concert.domain.waitingqueue;

import com.example.hhplus.concert.domain.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum WaitingQueueErrorCode implements ErrorCode {
  WAITING_QUEUE_NOT_FOUND(HttpStatus.NOT_FOUND,
      WaitingQueueConstants.WAITING_QUEUE_NOT_FOUND_MESSAGE),
  WAITING_QUEUE_EXPIRED(HttpStatus.BAD_REQUEST,
      WaitingQueueConstants.WAITING_QUEUE_EXPIRED_MESSAGE),
  INVALID_STATUS(HttpStatus.BAD_REQUEST, WaitingQueueConstants.INVALID_STATUS_MESSAGE),
  INVALID_EXPIRED_AT(HttpStatus.BAD_REQUEST, WaitingQueueConstants.INVALID_EXPIRED_AT_MESSAGE),
  ;

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
