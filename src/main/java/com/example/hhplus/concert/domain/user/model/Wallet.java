package com.example.hhplus.concert.domain.user.model;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.user.UserConstants;
import com.example.hhplus.concert.domain.user.UserErrorCode;
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
@Table(name = "wallet")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private int amount;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(insertable = false)
  private LocalDateTime updatedAt;

  public void chargeAmount(Integer amount) {
    if (amount == null || amount <= 0) {
      throw new BusinessException(UserErrorCode.INVALID_AMOUNT);
    }

    if (this.amount + amount > UserConstants.MAX_WALLET_AMOUNT) {
      throw new BusinessException(UserErrorCode.EXCEED_LIMIT_AMOUNT);
    }

    this.amount += amount;
  }

  public void withdrawAmount(Integer amount) {
    if (amount == null || amount <= 0) {
      throw new BusinessException(UserErrorCode.INVALID_AMOUNT);
    }

    if (this.amount - amount < 0) {
      throw new BusinessException(UserErrorCode.NOT_ENOUGH_BALANCE);
    }

    this.amount -= amount;
  }

}
