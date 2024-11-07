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

  private Long concertId;

  private String uuid;

  private Double score;

  private WaitingQueueStatus status;

  private LocalDateTime expiredAt;

  private LocalDateTime createdAt;

  @Builder
  public WaitingQueue(Long concertId, String uuid,
      Double score,
      WaitingQueueStatus status,
      LocalDateTime expiredAt, LocalDateTime createdAt) {
    this.concertId = concertId;
    this.uuid = uuid;
    this.score = score;
    this.status = status;
    this.expiredAt = expiredAt;
    this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
  }

  public void activate(LocalDateTime expiredAt) {
    if (this.status == WaitingQueueStatus.PROCESSING) {
      throw new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_ALREADY_ACTIVATED);
    }

    if (this.expiredAt != null || this.status == WaitingQueueStatus.EXPIRED) {
      throw new CoreException(ErrorType.WaitingQueue.INVALID_STATUS);
    }

    if (expiredAt.isBefore(LocalDateTime.now())) {
      throw new CoreException(ErrorType.WaitingQueue.INVALID_EXPIRED_AT);
    }

    this.status = WaitingQueueStatus.PROCESSING;
    this.expiredAt = expiredAt;
  }

  public void validateNotExpired() {
    if (this.status == WaitingQueueStatus.EXPIRED) {
      throw new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_EXPIRED);
    }

    if (this.expiredAt != null && LocalDateTime.now().isAfter(this.expiredAt)) {
      throw new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_EXPIRED);
    }
  }

  public void validateProcessing() {
    if (this.status == WaitingQueueStatus.EXPIRED) {
      throw new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_EXPIRED);
    }

    if (this.status != WaitingQueueStatus.PROCESSING) {
      throw new CoreException(ErrorType.WaitingQueue.INVALID_STATUS);
    }

    if (LocalDateTime.now().isAfter(this.expiredAt)) {
      throw new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_EXPIRED);
    }
  }

  public void validateConcertId(Long concertId) {
    if (!this.concertId.equals(concertId)) {
      throw new CoreException(ErrorType.Concert.INVALID_CONCERT_ID);
    }
  }
}
