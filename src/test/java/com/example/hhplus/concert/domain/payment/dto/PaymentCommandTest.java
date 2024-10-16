package com.example.hhplus.concert.domain.payment.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hhplus.concert.domain.payment.PaymentConstants;
import com.example.hhplus.concert.domain.payment.dto.PaymentCommand.CreatePaymentCommand;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Payment Command 테스트")
class PaymentCommandTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }


  @Nested
  @DisplayName("결제 생성 Command 테스트")
  class CreatePaymentCommandTest {

    @Test
    @DisplayName("결제 생성 Command 생성 실패 - reservationId가 null인 경우")
    void createPaymentCommandWithNullReservationId() {
      // given
      final Long reservationId = null;
      final Long userId = 1L;
      final Integer amount = 1000;

      // wheSet<n
      final Set<ConstraintViolation<CreatePaymentCommand>> violations = validator.validate(
          new CreatePaymentCommand(reservationId, userId, amount));

      final ConstraintViolation<CreatePaymentCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("reservationId"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(PaymentConstants.RESERVATION_ID_NULL_MESSAGE);
    }

    @Test
    @DisplayName("결제 생성 Command 생성 실패 - userId가 null인 경우")
    void createPaymentCommandWithNullUserId() {
      // given
      final Long reservationId = 1L;
      final Long userId = null;
      final Integer amount = 1000;

      // when
      final Set<ConstraintViolation<CreatePaymentCommand>> violations = validator.validate(
          new CreatePaymentCommand(reservationId, userId, amount));

      final ConstraintViolation<CreatePaymentCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("userId"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(PaymentConstants.USER_ID_NULL_MESSAGE);
    }

    @Test
    @DisplayName("결제 생성 Command 생성 실패 - amount가 null인 경우")
    void createPaymentCommandWithNullAmount() {
      // given
      final Long reservationId = 1L;
      final Long userId = 1L;
      final Integer amount = null;

      // when
      final Set<ConstraintViolation<CreatePaymentCommand>> violations = validator.validate(
          new CreatePaymentCommand(reservationId, userId, amount));

      final ConstraintViolation<CreatePaymentCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("amount"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(
          PaymentConstants.AMOUNT_MUST_NOT_BE_NULL_MESSAGE);
    }

    @Test
    @DisplayName("결제 생성 Command 생성 실패 - amount가 음수인 경우")
    void createPaymentCommandWithNegativeAmount() {
      // given
      final Long reservationId = 1L;
      final Long userId = 1L;
      final Integer amount = -1;

      // when
      final Set<ConstraintViolation<CreatePaymentCommand>> violations = validator.validate(
          new CreatePaymentCommand(reservationId, userId, amount));

      final ConstraintViolation<CreatePaymentCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("amount"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(
          PaymentConstants.AMOUNT_MUST_BE_POSITIVE_MESSAGE);
    }

    @Test
    @DisplayName("결제 생성 Command 생성 실패 - amount가 0인 경우")
    void createPaymentCommandWithZeroAmount() {
      // given
      final Long reservationId = 1L;
      final Long userId = 1L;
      final Integer amount = 0;

      // when
      final Set<ConstraintViolation<CreatePaymentCommand>> violations = validator.validate(
          new CreatePaymentCommand(reservationId, userId, amount));

      final ConstraintViolation<CreatePaymentCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("amount"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(
          PaymentConstants.AMOUNT_MUST_BE_POSITIVE_MESSAGE);
    }
  }
}