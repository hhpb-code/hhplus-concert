package com.example.hhplus.concert.domain.waitingqueue;

public class WaitingQueueConstants {

  public static final String WAITING_QUEUE_NOT_FOUND_MESSAGE = "대기열을 찾을 수 없습니다.";
  public static final String CONCERT_ID_NULL_MESSAGE = "콘서트 ID는 필수입니다.";
  public static final String WAITING_QUEUE_EXPIRED_MESSAGE = "대기열이 만료되었습니다.";
  public static final String WAITING_QUEUE_STATUS_NULL_MESSAGE = "대기열 상태는 필수입니다.";
  public static final String AVAILABLE_SLOTS_NULL_MESSAGE = "대기열 활성화 가능한 슬롯 수는 필수입니다.";
  public static final String INVALID_STATUS_MESSAGE = "대기열 상태가 유효하지 않습니다.";
  public static final String INVALID_EXPIRED_AT_MESSAGE = "만료 시간이 유효하지 않습니다.";
  public static final String WAITING_QUEUE_ID_NULL_MESSAGE = "대기열 ID는 필수입니다.";
  public static final String WAITING_QUEUE_UUID_EMPTY_MESSAGE = "대기열 UUID는 필수입니다.";
  public static final String INVALID_CONCERT_ID_MESSAGE = "콘서트 ID가 유효하지 않습니다.";


  // NOTE: 테스트를 용이하게 하기 위해 작은 값으로 설정
  public static final int MAX_PROCESSING_WAITING_QUEUE_COUNT = 3;
  public static final int WAITING_QUEUE_EXPIRE_MINUTES = 10;

}