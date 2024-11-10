package com.example.hhplus.concert.domain.support;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DistributedLockType {
  CONCERT_SEAT("concertSeatLock"),
  USER_WALLET("userWalletLock"),
  RESERVATION("reservationLock"),
  WAITING_QUEUE("waitingQueueLock");

  private final String lockName;

  public String lockName() {
    return lockName;
  }
}