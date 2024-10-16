package com.example.hhplus.concert.domain.user;

import com.example.hhplus.concert.domain.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, UserConstants.USER_NOT_FOUND_MESSAGE),
  WALLET_NOT_FOUND(HttpStatus.NOT_FOUND, UserConstants.WALLET_NOT_FOUND_MESSAGE),
  INVALID_AMOUNT(HttpStatus.BAD_REQUEST, UserConstants.INVALID_AMOUNT_MESSAGE),
  ZERO_AMOUNT(HttpStatus.BAD_REQUEST, UserConstants.ZERO_AMOUNT_MESSAGE),
  NEGATIVE_AMOUNT(HttpStatus.BAD_REQUEST, UserConstants.NEGATIVE_AMOUNT_MESSAGE),
  EXCEED_LIMIT_AMOUNT(HttpStatus.BAD_REQUEST, UserConstants.EXCEED_LIMIT_AMOUNT_MESSAGE),
  WALLET_NOT_MATCH_USER(HttpStatus.BAD_REQUEST, UserConstants.WALLET_NOT_MATCH_USER_MESSAGE),
  NOT_ENOUGH_BALANCE(HttpStatus.BAD_REQUEST, UserConstants.NOT_ENOUGH_BALANCE_MESSAGE),
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
