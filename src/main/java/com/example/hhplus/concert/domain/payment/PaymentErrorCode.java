package com.example.hhplus.concert.domain.payment;

import com.example.hhplus.concert.domain.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {
  PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "결제 정보를 찾을 수 없습니다."),
  RESERVATION_ID_NULL(HttpStatus.BAD_REQUEST, "예약 ID는 필수입니다."),
  USER_ID_NULL(HttpStatus.BAD_REQUEST, "사용자 ID는 필수입니다."),
  AMOUNT_MUST_NOT_BE_NULL(HttpStatus.BAD_REQUEST, "금액은 필수입니다."),
  AMOUNT_MUST_BE_POSITIVE(HttpStatus.BAD_REQUEST, "금액은 0보다 커야 합니다."),
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
