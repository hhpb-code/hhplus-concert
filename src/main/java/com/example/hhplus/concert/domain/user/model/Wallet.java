package com.example.hhplus.concert.domain.user.model;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.domain.support.model.BaseEntity;
import com.example.hhplus.concert.domain.user.UserConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "wallet")
@Getter
@NoArgsConstructor
public class Wallet extends BaseEntity {

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private int amount;

  @Builder
  public Wallet(Long id, Long userId, int amount, LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    super(id, createdAt, updatedAt);
    this.userId = userId;
    this.amount = amount;
  }

  public void chargeAmount(Integer amount) {
    if (amount == null || amount <= 0) {
      throw new CoreException(ErrorType.User.INVALID_AMOUNT);
    }

    if (this.amount + amount > UserConstants.MAX_WALLET_AMOUNT) {
      throw new CoreException(ErrorType.User.EXCEED_LIMIT_AMOUNT);
    }

    this.amount += amount;
  }

  public void withdrawAmount(Integer amount) {
    if (amount == null || amount <= 0) {
      throw new CoreException(ErrorType.User.INVALID_AMOUNT);
    }

    if (this.amount - amount < 0) {
      throw new CoreException(ErrorType.User.NOT_ENOUGH_BALANCE);
    }

    this.amount -= amount;
  }

}
