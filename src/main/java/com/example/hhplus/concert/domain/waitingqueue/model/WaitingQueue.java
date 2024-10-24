package com.example.hhplus.concert.domain.waitingqueue.model;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.domain.support.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "waiting_queue")
@Getter
@NoArgsConstructor
public class WaitingQueue extends BaseEntity {

  @Column(nullable = false)
  private Long concertId;

  @Column(nullable = false, unique = true)
  private String uuid;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private WaitingQueueStatus status;

  private LocalDateTime expiredAt;

  @Builder
  public WaitingQueue(Long id, Long concertId, String uuid, WaitingQueueStatus status,
      LocalDateTime expiredAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id, createdAt, updatedAt);
    this.concertId = concertId;
    this.uuid = uuid;
    this.status = status;
    this.expiredAt = expiredAt;
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

  public boolean isProcessing() {
    if (this.status != WaitingQueueStatus.PROCESSING) {
      return false;
    }

    return LocalDateTime.now().isBefore(this.expiredAt);
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
