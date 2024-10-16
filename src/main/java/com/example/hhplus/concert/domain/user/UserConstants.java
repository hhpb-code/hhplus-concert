package com.example.hhplus.concert.domain.user;

public class UserConstants {

  public static final String USER_ID_NULL_MESSAGE = "사용자 ID는 필수입니다.";
  public static final String WALLET_ID_NULL_MESSAGE = "지갑 ID는 필수입니다.";
  public static final String ZERO_AMOUNT_MESSAGE = "충전 금액은 0원일 수 없습니다.";
  public static final String NEGATIVE_AMOUNT_MESSAGE = "충전 금액은 음수일 수 없습니다.";
  public static final String EXCEED_LIMIT_AMOUNT_MESSAGE = "충전 금액이 한도를 초과했습니다.";
  public static final String INVALID_AMOUNT_MESSAGE = "유효하지 않은 충전 금액입니다.";
  public static final String WALLET_NOT_MATCH_USER_MESSAGE = "지갑이 사용자와 일치하지 않습니다.";
  public static final String WALLET_ID_MUST_NOT_BE_NULL_MESSAGE = "지갑 ID는 필수입니다.";
  public static final String AMOUNT_MUST_BE_POSITIVE_MESSAGE = "금액은 0보다 커야 합니다.";
  public static final String AMOUNT_MUST_NOT_BE_NULL_MESSAGE = "금액은 필수입니다.";

  public static final int MAX_WALLET_AMOUNT = 1000000;

}
