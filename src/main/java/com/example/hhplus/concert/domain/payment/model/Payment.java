package com.example.hhplus.concert.domain.payment.model;

import com.example.hhplus.concert.domain.common.exception.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "payment")
@Getter
@NoArgsConstructor
public class Payment extends BaseEntity {

  @Column(nullable = false)
  private Long reservationId;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private Integer amount;

  @Builder
  public Payment(Long id, Long reservationId, Long userId, Integer amount,
      LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id, createdAt, updatedAt);
    this.reservationId = reservationId;
    this.userId = userId;
    this.amount = amount;
  }

}
