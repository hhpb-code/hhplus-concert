package com.example.hhplus.concert.domain.event.model;

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

  @Builder
  public OutboxEvent(Long id, OutboxEventStatus status, String type, String payload,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    super(id, createdAt, updatedAt);
    this.status = status;
    this.type = type;
    this.payload = payload;
  }

  public void publish() {
    if (this.status == OutboxEventStatus.PUBLISHED) {
      throw new CoreException(ErrorType.OutboxEvent.OUTBOX_EVENT_ALREADY_PUBLISHED);
    }

    this.status = OutboxEventStatus.PUBLISHED;
  }
}
