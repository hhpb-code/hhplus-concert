package com.example.hhplus.concert.domain.concert;

import com.example.hhplus.concert.domain.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ConcertErrorCode implements ErrorCode {
  CONCERT_NOT_FOUND(HttpStatus.NOT_FOUND, ConcertConstants.CONCERT_NOT_FOUND),
  CONCERT_SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, ConcertConstants.CONCERT_SCHEDULE_NOT_FOUND),
  CONCERT_SEAT_NOT_FOUND(HttpStatus.NOT_FOUND, ConcertConstants.CONCERT_SEAT_NOT_FOUND),
  RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, ConcertConstants.RESERVATION_NOT_FOUND),
  CONCERT_SCHEDULE_NOT_RESERVABLE(HttpStatus.BAD_REQUEST,
      ConcertConstants.CONCERT_SCHEDULE_NOT_RESERVABLE),
  CONCERT_SEAT_ALREADY_RESERVED(HttpStatus.BAD_REQUEST,
      ConcertConstants.CONCERT_SEAT_ALREADY_RESERVED),
  RESERVATION_ALREADY_PAID(HttpStatus.BAD_REQUEST, ConcertConstants.RESERVATION_ALREADY_PAID),
  RESERVATION_ALREADY_CANCELED(HttpStatus.BAD_REQUEST,
      ConcertConstants.RESERVATION_ALREADY_CANCELED),
  CONCERT_SEAT_NOT_RESERVED(HttpStatus.BAD_REQUEST, ConcertConstants.CONCERT_SEAT_NOT_RESERVED),
  RESERVATION_USER_NOT_MATCHED(HttpStatus.BAD_REQUEST,
      ConcertConstants.RESERVATION_USER_NOT_MATCHED),
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
