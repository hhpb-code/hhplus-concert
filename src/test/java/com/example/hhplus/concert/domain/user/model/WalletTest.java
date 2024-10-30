package com.example.hhplus.concert.domain.user.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.domain.user.UserConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Wallet 단위 테스트")
class WalletTest {

  @Nested
  @DisplayName("충전 테스트")
  class ChargeAmountTest {

    @Test
    @DisplayName("충전 실패 - 금액이 null인 경우")
    void shouldThrowBusinessExceptionWhenAmountIsNull() {
      // given
      final Wallet wallet = Wallet.builder().amount(1000).build();

      // when
      final CoreException result = assertThrows(CoreException.class, () -> {
        wallet.chargeAmount(null);
      });

      // then
      assertThat(result.getErrorType()).isEqualTo(ErrorType.User.INVALID_AMOUNT);
    }

    @Test
    @DisplayName("충전 실패 - 금액이 음수인 경우")
    void shouldThrowBusinessExceptionWhenAmountIsNegative() {
      // given
      final Wallet wallet = Wallet.builder().amount(1000).build();

      // when
      final CoreException result = assertThrows(CoreException.class, () -> {
        wallet.chargeAmount(-1);
      });

      // then
      assertThat(result.getErrorType()).isEqualTo(ErrorType.User.INVALID_AMOUNT);
    }

    @Test
    @DisplayName("충전 실패 - 금액이 0인 경우")
    void shouldThrowBusinessExceptionWhenAmountIsZero() {
      // given
      final Wallet wallet = Wallet.builder().amount(1000).build();

      // when
      final CoreException result = assertThrows(CoreException.class, () -> {
        wallet.chargeAmount(0);
      });

      // then
      assertThat(result.getErrorType()).isEqualTo(ErrorType.User.INVALID_AMOUNT);
    }

    @Test
    @DisplayName("충전 실패 - 한도 초과인 경우")
    void shouldThrowBusinessExceptionWhenAmountIsExceedLimit() {
      // given
      final Wallet wallet = Wallet.builder().amount(0).build();

      // when
      final CoreException result = assertThrows(CoreException.class, () -> {
        wallet.chargeAmount(UserConstants.MAX_WALLET_AMOUNT + 1);
      });

      // then
      assertThat(result.getErrorType()).isEqualTo(ErrorType.User.EXCEED_LIMIT_AMOUNT);
    }

    @Test
    @DisplayName("충전 성공")
    void shouldSuccessfullyChargeAmount() {
      // given
      final Wallet wallet = Wallet.builder().amount(0).build();

      // when
      wallet.chargeAmount(UserConstants.MAX_WALLET_AMOUNT);

      // then
      assertThat(wallet.getAmount()).isEqualTo(UserConstants.MAX_WALLET_AMOUNT);
    }

  }

  @Nested
  @DisplayName("출금 테스트")
  class WithdrawAmountTest {

    @Test
    @DisplayName("출금 실패 - 금액이 null인 경우")
    void shouldThrowBusinessExceptionWhenAmountIsNull() {
      // given
      final Wallet wallet = Wallet.builder().amount(1000).build();

      // when
      final CoreException result = assertThrows(CoreException.class, () -> {
        wallet.withdrawAmount(null);
      });

      // then
      assertThat(result.getErrorType()).isEqualTo(ErrorType.User.INVALID_AMOUNT);
    }

    @Test
    @DisplayName("출금 실패 - 금액이 음수인 경우")
    void shouldThrowBusinessExceptionWhenAmountIsNegative() {
      // given
      final Wallet wallet = Wallet.builder().amount(1000).build();

      // when
      final CoreException result = assertThrows(CoreException.class, () -> {
        wallet.withdrawAmount(-1);
      });

      // then
      assertThat(result.getErrorType()).isEqualTo(ErrorType.User.INVALID_AMOUNT);
    }

    @Test
    @DisplayName("출금 실패 - 금액이 0인 경우")
    void shouldThrowBusinessExceptionWhenAmountIsZero() {
      // given
      final Wallet wallet = Wallet.builder().amount(1000).build();

      // when
      final CoreException result = assertThrows(CoreException.class, () -> {
        wallet.withdrawAmount(0);
      });

      // then
      assertThat(result.getErrorType()).isEqualTo(ErrorType.User.INVALID_AMOUNT);
    }

    @Test
    @DisplayName("출금 실패 - 잔액 부족인 경우")
    void shouldThrowBusinessExceptionWhenAmountIsNotEnough() {
      // given
      final Wallet wallet = Wallet.builder().amount(1000).build();

      // when
      final CoreException result = assertThrows(CoreException.class, () -> {
        wallet.withdrawAmount(1001);
      });

      // then
      assertThat(result.getErrorType()).isEqualTo(ErrorType.User.NOT_ENOUGH_BALANCE);
    }

    @Test
    @DisplayName("출금 성공")
    void shouldSuccessfullyWithdrawAmount() {
      // given
      final Wallet wallet = Wallet.builder().amount(1000).build();

      // when
      wallet.withdrawAmount(500);

      // then
      assertThat(wallet.getAmount()).isEqualTo(500);
    }
  }

}