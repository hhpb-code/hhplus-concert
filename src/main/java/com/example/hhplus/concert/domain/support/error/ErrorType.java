package com.example.hhplus.concert.domain.support.error;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.logging.LogLevel;

@AllArgsConstructor
@Getter
public enum ErrorType implements IErrorType {
  ;

  private final ErrorCode code;
  private final String message;
  private final LogLevel logLevel;

  @AllArgsConstructor
  public enum WaitingQueue implements IErrorType {
    WAITING_QUEUE_NOT_FOUND(ErrorCode.NOT_FOUND, "대기열 정보를 찾을 수 없습니다.", LogLevel.WARN),
    WAITING_QUEUE_EXPIRED(ErrorCode.BAD_REQUEST, "대기열이 만료되었습니다.", LogLevel.WARN),
    WAITING_QUEUE_ALREADY_ACTIVATED(ErrorCode.BAD_REQUEST, "대기열이 이미 활성 상태입니다.", LogLevel.WARN),
    INVALID_STATUS(ErrorCode.BAD_REQUEST, "대기열 상태가 유효하지 않습니다.", LogLevel.WARN),
    INVALID_EXPIRED_AT(ErrorCode.BAD_REQUEST, "만료 시간이 유효하지 않습니다.", LogLevel.WARN),
    WAITING_QUEUE_ID_MUST_NOT_BE_NULL(ErrorCode.BAD_REQUEST, "대기열 ID는 null일 수 없습니다.",
        LogLevel.WARN),
    WAITING_QUEUE_UUID_MUST_NOT_BE_EMPTY(ErrorCode.BAD_REQUEST, "대기열 UUID는 비어 있을 수 없습니다.",
        LogLevel.WARN),
    WAITING_QUEUE_STATUS_MUST_NOT_BE_NULL(ErrorCode.BAD_REQUEST, "대기열 상태는 null일 수 없습니다.",
        LogLevel.WARN),
    AVAILABLE_SLOTS_MUST_NOT_BE_NULL(ErrorCode.BAD_REQUEST, "사용 가능한 슬롯은 null일 수 없습니다.",
        LogLevel.WARN),
    AVAILABLE_SLOTS_MUST_BE_POSITIVE(ErrorCode.BAD_REQUEST, "사용 가능한 슬롯은 0보다 커야 합니다.",
        LogLevel.WARN),
    ;

    private final ErrorCode code;
    private final String message;
    private final LogLevel logLevel;

    @Override
    public ErrorCode getCode() {
      return code;
    }

    @Override
    public String getMessage() {
      return message;
    }

    @Override
    public LogLevel getLogLevel() {
      return logLevel;
    }
  }

  @AllArgsConstructor
  public enum Concert implements IErrorType {
    INVALID_CONCERT_ID(ErrorCode.BAD_REQUEST, "콘서트 ID가 유효하지 않습니다.", LogLevel.WARN),
    CONCERT_ID_MUST_NOT_BE_NULL(ErrorCode.BAD_REQUEST, "콘서트 ID는 null일 수 없습니다.", LogLevel.WARN),
    ;

    private final ErrorCode code;
    private final String message;
    private final LogLevel logLevel;

    @Override
    public ErrorCode getCode() {
      return code;
    }

    @Override
    public String getMessage() {
      return message;
    }

    @Override
    public LogLevel getLogLevel() {
      return logLevel;
    }
  }

  @AllArgsConstructor
  public enum User implements IErrorType {
    USER_NOT_FOUND(ErrorCode.NOT_FOUND, "사용자를 찾을 수 없습니다.", LogLevel.WARN),
    WALLET_NOT_FOUND(ErrorCode.NOT_FOUND, "지갑을 찾을 수 없습니다.", LogLevel.WARN),
    WALLET_NOT_MATCH_USER(ErrorCode.BAD_REQUEST, "지갑이 사용자와 일치하지 않습니다.", LogLevel.WARN),
    INVALID_AMOUNT(ErrorCode.BAD_REQUEST, "유효하지 않은 충전 금액입니다.", LogLevel.WARN),
    EXCEED_LIMIT_AMOUNT(ErrorCode.BAD_REQUEST, "충전 금액이 한도를 초과했습니다.", LogLevel.WARN),
    NOT_ENOUGH_BALANCE(ErrorCode.BAD_REQUEST, "잔액이 부족합니다.", LogLevel.WARN),
    USER_ID_MUST_NOT_BE_NULL(ErrorCode.BAD_REQUEST, "사용자 ID는 null일 수 없습니다.", LogLevel.WARN),
    WALLET_ID_MUST_NOT_BE_NULL(ErrorCode.BAD_REQUEST, "지갑 ID는 null일 수 없습니다.", LogLevel.WARN),
    AMOUNT_MUST_NOT_BE_NULL(ErrorCode.BAD_REQUEST, "금액은 null일 수 없습니다.", LogLevel.WARN),
    AMOUNT_MUST_BE_POSITIVE(ErrorCode.BAD_REQUEST, "금액은 0보다 커야 합니다.", LogLevel.WARN),
    ;

    private final ErrorCode code;
    private final String message;
    private final LogLevel logLevel;

    @Override
    public ErrorCode getCode() {
      return code;
    }

    @Override
    public String getMessage() {
      return message;
    }

    @Override
    public LogLevel getLogLevel() {
      return logLevel;
    }
  }
}
