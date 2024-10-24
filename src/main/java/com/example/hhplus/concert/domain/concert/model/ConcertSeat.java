package com.example.hhplus.concert.domain.concert.model;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.domain.support.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "concert_seat")
@Getter
@NoArgsConstructor
public class ConcertSeat extends BaseEntity {

  @Column(nullable = false)
  private Long concertScheduleId;

  @Column(nullable = false)
  private Integer number;

  @Column(nullable = false)
  private Integer price;

  @Column(nullable = false)
  private Boolean isReserved;

  @Version
  private Long version;

  @Builder
  public ConcertSeat(Long id, Long concertScheduleId, Integer number, Integer price,
      Boolean isReserved, LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id, createdAt, updatedAt);
    this.concertScheduleId = concertScheduleId;
    this.number = number;
    this.price = price;
    this.isReserved = isReserved;
  }

  public void reserve() {
    if (this.isReserved) {
      throw new CoreException(ErrorType.Concert.CONCERT_SEAT_ALREADY_RESERVED);
    }

    this.isReserved = true;
  }

  public void release() {
    if (!this.isReserved) {
      throw new CoreException(ErrorType.Concert.CONCERT_SEAT_NOT_RESERVED);
    }

    this.isReserved = false;
  }

  public void validateReserved() {
    if (!this.isReserved) {
      throw new CoreException(ErrorType.Concert.CONCERT_SEAT_NOT_RESERVED);
    }
  }
}
