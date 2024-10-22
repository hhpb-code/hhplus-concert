package com.example.hhplus.concert.domain.waitingqueue;

import com.example.hhplus.concert.domain.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum WaitingQueueErrorCode implements ErrorCode {
  WAITING_QUEUE_NOT_FOUND(HttpStatus.NOT_FOUND, "대기열을 찾을 수 없습니다."),
  WAITING_QUEUE_EXPIRED(HttpStatus.BAD_REQUEST, "대기열이 만료되었습니다."),
  INVALID_STATUS(HttpStatus.BAD_REQUEST, "대기열 상태가 유효하지 않습니다."),
  INVALID_EXPIRED_AT(HttpStatus.BAD_REQUEST, "만료 시간이 유효하지 않습니다."),
  INVALID_CONCERT_ID(HttpStatus.BAD_REQUEST, "콘서트 ID가 유효하지 않습니다."),
  WAITING_QUEUE_ID_MUST_NOT_BE_NULL(HttpStatus.BAD_REQUEST, "대기열 ID는 null일 수 없습니다."),
  WAITING_QUEUE_UUID_MUST_NOT_BE_EMPTY(HttpStatus.BAD_REQUEST, "대기열 UUID는 비어 있을 수 없습니다."),
  WAITING_QUEUE_STATUS_MUST_NOT_BE_NULL(HttpStatus.BAD_REQUEST, "대기열 상태는 null일 수 없습니다."),
  CONCERT_ID_MUST_NOT_BE_NULL(HttpStatus.BAD_REQUEST, "콘서트 ID는 null일 수 없습니다."),
  AVAILABLE_SLOTS_MUST_NOT_BE_NULL(HttpStatus.BAD_REQUEST, "사용 가능한 슬롯은 null일 수 없습니다."),
  AVAILABLE_SLOTS_MUST_BE_POSITIVE(HttpStatus.BAD_REQUEST, "사용 가능한 슬롯은 0보다 커야 합니다.");

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
