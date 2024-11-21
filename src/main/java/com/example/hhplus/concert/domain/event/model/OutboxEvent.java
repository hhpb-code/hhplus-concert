package com.example.hhplus.concert.domain.event.model;

import com.example.hhplus.concert.domain.event.EventConstants;
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
@Table(name = "outbox_event")
@Getter
@NoArgsConstructor
public class OutboxEvent extends BaseEntity {

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private OutboxEventStatus status;

  @Column(nullable = false)
  private String type;

  @Column(nullable = false)
  private String payload;

  @Column(nullable = false)
  private int retryCount = 0;

  private LocalDateTime retryAt;

  @Builder
  public OutboxEvent(Long id, OutboxEventStatus status, String type, String payload,
      int retryCount,
      LocalDateTime retryAt,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    super(id, createdAt, updatedAt);
    this.status = status;
    this.type = type;
    this.payload = payload;
    this.retryCount = retryCount;
    this.retryAt = retryAt;
  }

  public void publish() {
    if (this.status == OutboxEventStatus.PUBLISHED) {
      throw new CoreException(ErrorType.OutboxEvent.OUTBOX_EVENT_ALREADY_PUBLISHED);
    }

    this.status = OutboxEventStatus.PUBLISHED;
  }

  public void fail() {
    if (this.status == OutboxEventStatus.FAILED) {
      throw new CoreException(ErrorType.OutboxEvent.OUTBOX_EVENT_ALREADY_FAILED);
    }

    this.status = OutboxEventStatus.FAILED;
    this.retryAt = LocalDateTime.now().plusMinutes(EventConstants.RETRY_INTERVAL_MINUTES);
  }

  public void retry() {
    if (this.status != OutboxEventStatus.FAILED) {
      throw new CoreException(ErrorType.OutboxEvent.OUTBOX_EVENT_NOT_FAILED);
    }
    if (this.retryCount >= EventConstants.MAX_RETRY_COUNT) {
      throw new CoreException(ErrorType.OutboxEvent.OUTBOX_EVENT_RETRY_EXCEEDED);
    }
    if (this.retryAt == null) {
      throw new CoreException(ErrorType.OutboxEvent.OUTBOX_EVENT_UPDATED_AT_NULL);
    }
    LocalDateTime now = LocalDateTime.now();
    if (this.retryAt.isAfter(now)) {
      throw new CoreException(ErrorType.OutboxEvent.OUTBOX_EVENT_RETRY_INTERVAL_NOT_PASSED);
    }

    this.retryCount++;
    this.status = OutboxEventStatus.PENDING;
  }
}
