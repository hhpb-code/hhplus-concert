package com.example.hhplus.concert.domain.user.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.domain.user.dto.UserCommand.ChargeUserWalletAmountByWalletIdCommand;
import com.example.hhplus.concert.domain.user.dto.UserCommand.WithdrawUserWalletAmountCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("User Command 단위 테스트")
class UserCommandTest {

  @Nested
  @DisplayName("잔액 충전 Command 테스트")
  class ChargeUserWalletAmountByWalletIdCommandTest {

    @Test
    @DisplayName("잔액 충전 Command 생성 실패 - walletId가 null인 경우")
    void shouldThrowBusinessExceptionWhenWalletIdIsNull() {
      // given
      final Long walletId = null;
      final Integer amount = 1000;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new ChargeUserWalletAmountByWalletIdCommand(walletId, amount));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.User.WALLET_ID_MUST_NOT_BE_NULL.getMessage());
    }

    @Test
    @DisplayName("잔액 충전 Command 생성 실패 - 금액이 null인 경우")
    void shouldThrowBusinessExceptionWhenAmountIsNull() {
      // given
      final Long walletId = 1L;
      final Integer amount = null;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new ChargeUserWalletAmountByWalletIdCommand(walletId, amount));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.User.AMOUNT_MUST_NOT_BE_NULL.getMessage());
    }

    @Test
    @DisplayName("잔액 충전 Command 생성 실패 - 금액이 음수인 경우")
    void shouldThrowBusinessExceptionWhenAmountIsNegative() {
      // given
      final Long walletId = 1L;
      final Integer amount = -1;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new ChargeUserWalletAmountByWalletIdCommand(walletId, amount));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.User.AMOUNT_MUST_BE_POSITIVE.getMessage());
    }

    @Test
    @DisplayName("잔액 충전 Command 생성 실패 - 금액이 0인 경우")
    void shouldThrowBusinessExceptionWhenAmountIsZero() {
      // given
      final Long walletId = 1L;
      final Integer amount = 0;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new ChargeUserWalletAmountByWalletIdCommand(walletId, amount));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.User.AMOUNT_MUST_BE_POSITIVE.getMessage());
    }

    @Test
    @DisplayName("잔액 충전 Command 생성 성공")
    void shouldSuccessfullyCreateChargeUserWalletAmountByWalletIdCommand() {
      // given
      final Long walletId = 1L;
      final Integer amount = 1000;

      // when
      final ChargeUserWalletAmountByWalletIdCommand command = new ChargeUserWalletAmountByWalletIdCommand(
          walletId, amount);

      // then
      assertThat(command).isNotNull();
      assertThat(command.walletId()).isEqualTo(walletId);
      assertThat(command.amount()).isEqualTo(amount);
    }

  }

  @Nested
  @DisplayName("잔액 출금 Command 테스트")
  class WithdrawUserWalletAmountCommandTest {

    @Test
    @DisplayName("잔액 출금 Command 생성 실패 - userId가 null인 경우")
    void shouldThrowBusinessExceptionWhenUserIdIsNull() {
      // given
      final Long userId = null;
      final Integer amount = 1000;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new WithdrawUserWalletAmountCommand(userId, amount));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.User.USER_ID_MUST_NOT_BE_NULL.getMessage());
    }

    @Test
    @DisplayName("잔액 출금 Command 생성 실패 - 금액이 null인 경우")
    void shouldThrowBusinessExceptionWhenAmountIsNull() {
      // given
      final Long userId = 1L;
      final Integer amount = null;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new WithdrawUserWalletAmountCommand(userId, amount));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.User.AMOUNT_MUST_NOT_BE_NULL.getMessage());
    }

    @Test
    @DisplayName("잔액 출금 Command 생성 실패 - 금액이 음수인 경우")
    void shouldThrowBusinessExceptionWhenAmountIsNegative() {
      // given
      final Long userId = 1L;
      final Integer amount = -1;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new WithdrawUserWalletAmountCommand(userId, amount));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.User.AMOUNT_MUST_BE_POSITIVE.getMessage());
    }

    @Test
    @DisplayName("잔액 출금 Command 생성 실패 - 금액이 0인 경우")
    void shouldThrowBusinessExceptionWhenAmountIsZero() {
      // given
      final Long userId = 1L;
      final Integer amount = 0;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new WithdrawUserWalletAmountCommand(userId, amount));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.User.AMOUNT_MUST_BE_POSITIVE.getMessage());
    }

    @Test
    @DisplayName("잔액 출금 Command 생성 성공")
    void shouldSuccessfullyCreateWithdrawUserWalletAmountCommand() {
      // given
      final Long userId = 1L;
      final Integer amount = 1000;

      // when
      final WithdrawUserWalletAmountCommand command = new WithdrawUserWalletAmountCommand(userId,
          amount);

      // then
      assertThat(command).isNotNull();
      assertThat(command.userId()).isEqualTo(userId);
      assertThat(command.amount()).isEqualTo(amount);
    }
  }

}
