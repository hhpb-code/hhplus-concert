package com.example.hhplus.concert.domain.concert.model;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.concert.ConcertErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@Table(name = "concert_seat")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConcertSeat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long concertScheduleId;

  @Column(nullable = false)
  private Integer number;

  @Column(nullable = false)
  private Integer price;

  @Column(nullable = false)
  private Boolean isReserved;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(insertable = false)
  private LocalDateTime updatedAt;

  public void reserve() {
    if (this.isReserved) {
      throw new BusinessException(ConcertErrorCode.CONCERT_SEAT_ALREADY_RESERVED);
    }

    this.isReserved = true;
  }

  public void release() {
    if (!this.isReserved) {
      throw new BusinessException(ConcertErrorCode.CONCERT_SEAT_NOT_RESERVED);
    }

    this.isReserved = false;
  }

  public void validateReserved() {
    if (!this.isReserved) {
      throw new BusinessException(ConcertErrorCode.CONCERT_SEAT_NOT_RESERVED);
    }
  }
}
