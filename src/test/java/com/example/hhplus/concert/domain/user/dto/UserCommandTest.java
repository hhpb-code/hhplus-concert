package com.example.hhplus.concert.domain.user.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hhplus.concert.domain.user.UserConstants;
import com.example.hhplus.concert.domain.user.dto.UserCommand.ChargeUserWalletAmountByWalletIdCommand;
import com.example.hhplus.concert.domain.user.dto.UserCommand.WithdrawUserWalletAmountCommand;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("User Command 단위 테스트")
class UserCommandTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Nested
  @DisplayName("잔액 충전 Commaand 테스트")
  class ChargeUserWalletAmountByWalletIdCommandTest {


    @Test
    @DisplayName("잔액 충전 Commaand 생성 실패 - walletId가 null인 경우")
    void shouldThrowIllegalArgumentExceptionWhenWalletIdIsNull() {
      // given
      final Long walletId = null;
      final Integer amount = 1000;
      final ChargeUserWalletAmountByWalletIdCommand command = new ChargeUserWalletAmountByWalletIdCommand(
          walletId, amount);

      // when
      final Set<ConstraintViolation<ChargeUserWalletAmountByWalletIdCommand>> violations = validator.validate(
          command);

      final ConstraintViolation<ChargeUserWalletAmountByWalletIdCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("walletId"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(
          UserConstants.WALLET_ID_MUST_NOT_BE_NULL_MESSAGE);
    }

    @Test
    @DisplayName("잔액 충전 Commaand 생성 실패 - 금액이 null인 경우")
    void shouldThrowIllegalArgumentExceptionWhenAmountIsNull() {
      // given
      final Long walletId = 1L;
      final Integer amount = null;
      final ChargeUserWalletAmountByWalletIdCommand command = new ChargeUserWalletAmountByWalletIdCommand(
          walletId, amount);

      // when
      final Set<ConstraintViolation<ChargeUserWalletAmountByWalletIdCommand>> violations = validator.validate(
          command);

      final ConstraintViolation<ChargeUserWalletAmountByWalletIdCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("amount"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(UserConstants.AMOUNT_MUST_NOT_BE_NULL_MESSAGE);
    }

    @Test
    @DisplayName("잔액 충전 Commaand 생성 실패 - 금액이 음수인 경우")
    void shouldThrowIllegalArgumentExceptionWhenAmountIsNegative() {
      // given
      final Long walletId = 1L;
      final Integer amount = -1;
      final ChargeUserWalletAmountByWalletIdCommand command = new ChargeUserWalletAmountByWalletIdCommand(
          walletId, amount);

      // when
      final Set<ConstraintViolation<ChargeUserWalletAmountByWalletIdCommand>> violations = validator.validate(
          command);

      final ConstraintViolation<ChargeUserWalletAmountByWalletIdCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("amount"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(UserConstants.AMOUNT_MUST_BE_POSITIVE_MESSAGE);
    }

    @Test
    @DisplayName("잔액 충전 Commaand 생성 실패 - 금액이 0인 경우")
    void shouldThrowIllegalArgumentExceptionWhenAmountIsZero() {
      // given
      final Long walletId = 1L;
      final Integer amount = 0;
      final ChargeUserWalletAmountByWalletIdCommand command = new ChargeUserWalletAmountByWalletIdCommand(
          walletId, amount);

      // when
      final Set<ConstraintViolation<ChargeUserWalletAmountByWalletIdCommand>> violations = validator.validate(
          command);

      final ConstraintViolation<ChargeUserWalletAmountByWalletIdCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("amount"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(UserConstants.AMOUNT_MUST_BE_POSITIVE_MESSAGE);
    }

    @Test
    @DisplayName("잔액 충전 Commaand 생성 성공")
    void shouldSuccessfullyCreateChargeUserWalletAmountByWalletIdCommand() {
      // given
      final Long walletId = 1L;
      final Integer amount = 1000;
      final ChargeUserWalletAmountByWalletIdCommand command = new ChargeUserWalletAmountByWalletIdCommand(
          walletId, amount);

      // when
      final Set<ConstraintViolation<ChargeUserWalletAmountByWalletIdCommand>> violations = validator.validate(
          command);

      // then
      assertThat(violations).isEmpty();
    }

  }

  @Nested
  @DisplayName("잔액 출금 Commaand 테스트")
  class WithdrawUserWalletAmountCommandTest {

    @Test
    @DisplayName("잔액 출금 Commaand 생성 실패 - userId가 null인 경우")
    void shouldThrowIllegalArgumentExceptionWhenWalletIdIsNull() {
      // given
      final Long userId = null;
      final Integer amount = 1000;
      final WithdrawUserWalletAmountCommand command = new WithdrawUserWalletAmountCommand(
          userId, amount);

      // when
      final Set<ConstraintViolation<WithdrawUserWalletAmountCommand>> violations = validator.validate(
          command);

      final ConstraintViolation<WithdrawUserWalletAmountCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("userId"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(
          UserConstants.USER_ID_NULL_MESSAGE);
    }

    @Test
    @DisplayName("잔액 출금 Commaand 생성 실패 - 금액이 null인 경우")
    void shouldThrowIllegalArgumentExceptionWhenAmountIsNull() {
      // given
      final Long userId = 1L;
      final Integer amount = null;
      final WithdrawUserWalletAmountCommand command = new WithdrawUserWalletAmountCommand(
          userId, amount);

      // when
      final Set<ConstraintViolation<WithdrawUserWalletAmountCommand>> violations = validator.validate(
          command);

      final ConstraintViolation<WithdrawUserWalletAmountCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("amount"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(UserConstants.AMOUNT_MUST_NOT_BE_NULL_MESSAGE);
    }

    @Test
    @DisplayName("잔액 출금 Commaand 생성 실패 - 금액이 음수인 경우")
    void shouldThrowIllegalArgumentExceptionWhenAmountIsNegative() {
      // given
      final Long userId = 1L;
      final Integer amount = -1;
      final WithdrawUserWalletAmountCommand command = new WithdrawUserWalletAmountCommand(
          userId, amount);

      // when
      final Set<ConstraintViolation<WithdrawUserWalletAmountCommand>> violations = validator.validate(
          command);

      final ConstraintViolation<WithdrawUserWalletAmountCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("amount"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(UserConstants.AMOUNT_MUST_BE_POSITIVE_MESSAGE);
    }

    @Test
    @DisplayName("잔액 출금 Commaand 생성 실패 - 금액이 0인 경우")
    void shouldThrowIllegalArgumentExceptionWhenAmountIsZero() {
      // given
      final Long userId = 1L;
      final Integer amount = 0;
      final WithdrawUserWalletAmountCommand command = new WithdrawUserWalletAmountCommand(
          userId, amount);

      // when
      final Set<ConstraintViolation<WithdrawUserWalletAmountCommand>> violations = validator.validate(
          command);

      final ConstraintViolation<WithdrawUserWalletAmountCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("amount"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(UserConstants.AMOUNT_MUST_BE_POSITIVE_MESSAGE);
    }

    @Test
    @DisplayName("잔액 출금 Commaand 생성 성공")
    void shouldSuccessfullyCreateWithdrawUserWalletAmountCommand() {
      // given
      final Long userId = 1L;
      final Integer amount = 1000;
      final WithdrawUserWalletAmountCommand command = new WithdrawUserWalletAmountCommand(
          userId, amount);

      // when
      final Set<ConstraintViolation<WithdrawUserWalletAmountCommand>> violations = validator.validate(
          command);

      // then
      assertThat(violations).isEmpty();
    }
  }

}