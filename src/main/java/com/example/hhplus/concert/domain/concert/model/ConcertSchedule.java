package com.example.hhplus.concert.domain.concert.model;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.common.exception.model.BaseEntity;
import com.example.hhplus.concert.domain.concert.ConcertErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "concert_schedule")
@Getter
@NoArgsConstructor
public class ConcertSchedule extends BaseEntity {

  @Column(nullable = false)
  private Long concertId;

  @Column(nullable = false)
  private LocalDateTime concertAt;

  @Column(nullable = false)
  private LocalDateTime reservationStartAt;

  @Column(nullable = false)
  private LocalDateTime reservationEndAt;

  @Builder
  public ConcertSchedule(Long id, Long concertId, LocalDateTime concertAt,
      LocalDateTime reservationStartAt, LocalDateTime reservationEndAt, LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    super(id, createdAt, updatedAt);
    this.concertId = concertId;
    this.concertAt = concertAt;
    this.reservationStartAt = reservationStartAt;
    this.reservationEndAt = reservationEndAt;
  }

  public void validateReservationTime() {
    LocalDateTime now = LocalDateTime.now();
    if (now.isBefore(reservationStartAt) || now.isAfter(reservationEndAt)) {
      throw new BusinessException(ConcertErrorCode.CONCERT_SCHEDULE_NOT_RESERVABLE);
    }
  }
}
