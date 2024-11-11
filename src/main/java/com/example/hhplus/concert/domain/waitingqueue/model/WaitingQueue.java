package com.example.hhplus.concert.domain.waitingqueue.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WaitingQueue {

  private String uuid;


  @Builder
  public WaitingQueue(String uuid) {
    this.uuid = uuid;
  }
}
