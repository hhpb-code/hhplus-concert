package com.example.hhplus.concert.domain.waitingqueue.model;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WaitingQueue {

  private String uuid;

  private LocalDateTime expiredAt;


  @Builder
  public WaitingQueue(
      String uuid,
      LocalDateTime expiredAt
  ) {
    this.uuid = uuid;
    this.expiredAt = expiredAt;
  }

  public void validateProcessing() {
    if (LocalDateTime.now().isAfter(expiredAt)) {
      throw new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_EXPIRED);
    }
  }
}
