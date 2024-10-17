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
@Table(name = "concert_schedule")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConcertSchedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long concertId;

  @Column(nullable = false)
  private LocalDateTime concertAt;

  @Column(nullable = false)
  private LocalDateTime reservationStartAt;

  @Column(nullable = false)
  private LocalDateTime reservationEndAt;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(insertable = false)
  private LocalDateTime updatedAt;

  public void validateReservationTime() {
    LocalDateTime now = LocalDateTime.now();
    if (now.isBefore(reservationStartAt) || now.isAfter(reservationEndAt)) {
      throw new BusinessException(ConcertErrorCode.CONCERT_SCHEDULE_NOT_RESERVABLE);
    }
  }
}
