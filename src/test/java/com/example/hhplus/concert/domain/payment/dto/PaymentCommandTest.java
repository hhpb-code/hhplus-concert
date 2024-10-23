package com.example.hhplus.concert.domain.payment.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.payment.dto.PaymentCommand.CreatePaymentCommand;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Payment Command 테스트")
class PaymentCommandTest {

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

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new CreatePaymentCommand(reservationId, userId, amount));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.Concert.RESERVATION_ID_MUST_NOT_BE_NULL.getMessage());
    }

    @Test
    @DisplayName("결제 생성 Command 생성 실패 - userId가 null인 경우")
    void createPaymentCommandWithNullUserId() {
      // given
      final Long reservationId = 1L;
      final Long userId = null;
      final Integer amount = 1000;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new CreatePaymentCommand(reservationId, userId, amount));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.User.USER_ID_MUST_NOT_BE_NULL.getMessage());
    }

    @Test
    @DisplayName("결제 생성 Command 생성 실패 - amount가 null인 경우")
    void createPaymentCommandWithNullAmount() {
      // given
      final Long reservationId = 1L;
      final Long userId = 1L;
      final Integer amount = null;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new CreatePaymentCommand(reservationId, userId, amount));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.User.AMOUNT_MUST_NOT_BE_NULL.getMessage());
    }

    @Test
    @DisplayName("결제 생성 Command 생성 실패 - amount가 음수인 경우")
    void createPaymentCommandWithNegativeAmount() {
      // given
      final Long reservationId = 1L;
      final Long userId = 1L;
      final Integer amount = -1;

      // when & then
      final Exception exception = assertThrows(Exception.class,
          () -> new CreatePaymentCommand(reservationId, userId, amount));

      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.User.AMOUNT_MUST_BE_POSITIVE.getMessage());
    }

    @Test
    @DisplayName("결제 생성 Command 생성 실패 - amount가 0인 경우")
    void createPaymentCommandWithZeroAmount() {
      // given
      final Long reservationId = 1L;
      final Long userId = 1L;
      final Integer amount = 0;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new CreatePaymentCommand(reservationId, userId, amount));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.User.AMOUNT_MUST_BE_POSITIVE.getMessage());
    }

    @Test
    @DisplayName("결제 생성 Command 생성 성공")
    void createPaymentCommand() {
      // given
      final Long reservationId = 1L;
      final Long userId = 1L;
      final Integer amount = 1000;

      // when
      final CreatePaymentCommand command = new CreatePaymentCommand(reservationId, userId, amount);

      // then
      assertThat(command).isNotNull();
      assertThat(command.reservationId()).isEqualTo(reservationId);
      assertThat(command.userId()).isEqualTo(userId);
      assertThat(command.amount()).isEqualTo(amount);
    }
  }
}
