package com.example.hhplus.concert.domain.concert.model;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.concert.ConcertErrorCode;
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
@Table(name = "reservation")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long concertSeatId;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ReservationStatus status;

  @Column(nullable = false)
  private LocalDateTime reservedAt;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(insertable = false)
  private LocalDateTime updatedAt;

  public void confirm() {
    if (status == ReservationStatus.CONFIRMED) {
      throw new BusinessException(ConcertErrorCode.RESERVATION_ALREADY_PAID);
    }

    if (status == ReservationStatus.CANCELED) {
      throw new BusinessException(ConcertErrorCode.RESERVATION_ALREADY_CANCELED);
    }

    this.status = ReservationStatus.CONFIRMED;
  }

  public void cancel() {
    if (status == ReservationStatus.CANCELED) {
      throw new BusinessException(ConcertErrorCode.RESERVATION_ALREADY_CANCELED);
    }

    if (status == ReservationStatus.CONFIRMED) {
      throw new BusinessException(ConcertErrorCode.RESERVATION_ALREADY_PAID);
    }

    this.status = ReservationStatus.CANCELED;
  }

  public void validateConfirmConditions(Long userId) {
    if (!this.userId.equals(userId)) {
      throw new BusinessException(ConcertErrorCode.RESERVATION_USER_NOT_MATCHED);
    }

    if (status == ReservationStatus.CANCELED) {
      throw new BusinessException(ConcertErrorCode.RESERVATION_ALREADY_CANCELED);
    }

    if (status == ReservationStatus.CONFIRMED) {
      throw new BusinessException(ConcertErrorCode.RESERVATION_ALREADY_PAID);
    }
  }
}
