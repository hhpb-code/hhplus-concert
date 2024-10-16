package com.example.hhplus.concert.domain.concert;

import com.example.hhplus.concert.domain.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ConcertErrorCode implements ErrorCode {
  CONCERT_SCHEDULE_NOT_RESERVABLE(HttpStatus.BAD_REQUEST,
      ConcertConstants.CONCERT_SCHEDULE_NOT_RESERVABLE),
  CONCERT_SEAT_ALREADY_RESERVED(HttpStatus.BAD_REQUEST,
      ConcertConstants.CONCERT_SEAT_ALREADY_RESERVED),
  RESERVATION_ALREADY_PAID(HttpStatus.BAD_REQUEST, ConcertConstants.RESERVATION_ALREADY_PAID),
  RESERVATION_ALREADY_CANCELED(HttpStatus.BAD_REQUEST,
      ConcertConstants.RESERVATION_ALREADY_CANCELED),
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
