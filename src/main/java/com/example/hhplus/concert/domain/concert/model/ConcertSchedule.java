package com.example.hhplus.concert.domain.concert.model;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.domain.support.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "concert_schedule",
    indexes = {
        @Index(
            name = "idx_concert_schedule",
            columnList = "concert_id, reservation_start_at, reservation_end_at"
        )
    }
)
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
      throw new CoreException(ErrorType.Concert.CONCERT_SCHEDULE_NOT_RESERVABLE);
    }
  }
}
