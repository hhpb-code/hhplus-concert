package com.example.hhplus.concert.domain.waitingqueue.model;

import lombok.Getter;

@Getter
public class WaitingQueueWithPosition extends WaitingQueue {

  private final Integer position;

  public WaitingQueueWithPosition(WaitingQueue waitingQueue, Integer position) {
    super(waitingQueue.getId(), waitingQueue.getConcertId(), waitingQueue.getUuid(),
        waitingQueue.getStatus(), waitingQueue.getExpiredAt(), waitingQueue.getCreatedAt(),
        waitingQueue.getUpdatedAt());
    this.position = position;
  }

}
