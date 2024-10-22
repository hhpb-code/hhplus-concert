package com.example.hhplus.concert.domain.user;

import com.example.hhplus.concert.domain.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
  WALLET_NOT_FOUND(HttpStatus.NOT_FOUND, "지갑을 찾을 수 없습니다."),
  INVALID_AMOUNT(HttpStatus.BAD_REQUEST, "유효하지 않은 충전 금액입니다."),
  EXCEED_LIMIT_AMOUNT(HttpStatus.BAD_REQUEST, "충전 금액이 한도를 초과했습니다."),
  WALLET_NOT_MATCH_USER(HttpStatus.BAD_REQUEST, "지갑이 사용자와 일치하지 않습니다."),
  NOT_ENOUGH_BALANCE(HttpStatus.BAD_REQUEST, "잔액이 부족합니다."),
  WALLET_ID_MUST_NOT_BE_NULL(HttpStatus.BAD_REQUEST, "지갑 ID는 필수입니다."),
  USER_ID_MUST_NOT_BE_NULL(HttpStatus.BAD_REQUEST, "사용자 ID는 필수입니다."),
  AMOUNT_MUST_NOT_BE_NULL(HttpStatus.BAD_REQUEST, "금액은 필수입니다."),
  AMOUNT_MUST_BE_POSITIVE(HttpStatus.BAD_REQUEST, "금액은 0보다 커야 합니다.");

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
