package com.example.hhplus.concert.domain.concert.model;

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
@Table(name = "reservation")
@Getter
@NoArgsConstructor
public class Reservation extends BaseEntity {

  @Column(nullable = false)
  private Long concertSeatId;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ReservationStatus status;

  @Column(nullable = false)
  private LocalDateTime reservedAt;

  @Builder
  public Reservation(Long id, Long concertSeatId, Long userId, ReservationStatus status,
      LocalDateTime reservedAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id, createdAt, updatedAt);
    this.concertSeatId = concertSeatId;
    this.userId = userId;
    this.status = status;
    this.reservedAt = reservedAt;
  }

  public void confirm() {
    if (status == ReservationStatus.CONFIRMED) {
      throw new CoreException(ErrorType.Concert.RESERVATION_ALREADY_PAID);
    }

    if (status == ReservationStatus.CANCELED) {
      throw new CoreException(ErrorType.Concert.RESERVATION_ALREADY_CANCELED);
    }

    this.status = ReservationStatus.CONFIRMED;
  }

  public void cancel() {
    if (status == ReservationStatus.CANCELED) {
      throw new CoreException(ErrorType.Concert.RESERVATION_ALREADY_CANCELED);
    }

    if (status == ReservationStatus.CONFIRMED) {
      throw new CoreException(ErrorType.Concert.RESERVATION_ALREADY_PAID);
    }

    this.status = ReservationStatus.CANCELED;
  }

  public void validateConfirmConditions(Long userId) {
    if (!this.userId.equals(userId)) {
      throw new CoreException(ErrorType.Concert.RESERVATION_USER_NOT_MATCHED);
    }

    if (status == ReservationStatus.CANCELED) {
      throw new CoreException(ErrorType.Concert.RESERVATION_ALREADY_CANCELED);
    }

    if (status == ReservationStatus.CONFIRMED) {
      throw new CoreException(ErrorType.Concert.RESERVATION_ALREADY_PAID);
    }
  }
}
