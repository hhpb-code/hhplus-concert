package com.example.hhplus.concert.domain.concert;

import com.example.hhplus.concert.domain.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ConcertErrorCode implements ErrorCode {
  CONCERT_NOT_FOUND(HttpStatus.NOT_FOUND, "콘서트를 찾을 수 없습니다."),
  CONCERT_SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "콘서트 스케줄을 찾을 수 없습니다."),
  CONCERT_SEAT_NOT_FOUND(HttpStatus.NOT_FOUND, "콘서트 좌석을 찾을 수 없습니다."),
  RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "예약을 찾을 수 없습니다."),
  CONCERT_SCHEDULE_NOT_RESERVABLE(HttpStatus.BAD_REQUEST, "콘서트 스케줄 예약이 불가능합니다."),
  CONCERT_SEAT_ALREADY_RESERVED(HttpStatus.BAD_REQUEST, "이미 예약된 좌석입니다."),
  RESERVATION_ALREADY_PAID(HttpStatus.BAD_REQUEST, "이미 결제된 예약입니다."),
  RESERVATION_ALREADY_CANCELED(HttpStatus.BAD_REQUEST, "이미 취소된 예약입니다."),
  CONCERT_SEAT_NOT_RESERVED(HttpStatus.BAD_REQUEST, "예약되지 않은 좌석입니다."),
  RESERVATION_USER_NOT_MATCHED(HttpStatus.BAD_REQUEST, "예약 사용자가 일치하지 않습니다."),
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
