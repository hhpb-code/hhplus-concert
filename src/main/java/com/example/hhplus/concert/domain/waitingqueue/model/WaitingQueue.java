package com.example.hhplus.concert.domain.waitingqueue.model;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "waiting_queue")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WaitingQueue {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long concertId;

  @Column(nullable = false)
  private String uuid;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private WaitingQueueStatus status;

  private LocalDateTime expiredAt;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(insertable = false)
  private LocalDateTime updatedAt;

  public void activate(LocalDateTime expiredAt) {
    if (this.status == WaitingQueueStatus.PROCESSING) {
      throw new BusinessException(WaitingQueueErrorCode.INVALID_STATUS);
    }

    if (this.expiredAt != null || this.status == WaitingQueueStatus.EXPIRED) {
      throw new BusinessException(WaitingQueueErrorCode.INVALID_STATUS);
    }

    if (expiredAt.isBefore(LocalDateTime.now())) {
      throw new BusinessException(WaitingQueueErrorCode.INVALID_EXPIRED_AT);
    }

    this.status = WaitingQueueStatus.PROCESSING;
    this.expiredAt = expiredAt;
  }
}
