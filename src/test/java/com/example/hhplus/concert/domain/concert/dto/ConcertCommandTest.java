package com.example.hhplus.concert.domain.concert.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hhplus.concert.domain.concert.ConcertConstants;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.CreateReservationCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.ReserveConcertSeatCommand;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("콘서트 Command 단위 테스트")
class ConcertCommandTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Nested
  @DisplayName("콘서트 좌석 예약 Command")
  class ReserveConcertSeatCommandTest {

    @Test
    @DisplayName("콘서트 좌석 예약 Command 생성 실패 - concertSeatId가 null인 경우")
    void shouldThrowExceptionWhenConcertSeatIdIsNull() {
      // given
      final Long concertSeatId = null;
      final Long userId = 1L;
      final ReserveConcertSeatCommand command = new ReserveConcertSeatCommand(
          concertSeatId, userId);

      // when
      final Set<ConstraintViolation<ReserveConcertSeatCommand>> violations = validator.validate(
          command);

      final ConstraintViolation<ReserveConcertSeatCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("concertSeatId"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(ConcertConstants.CONCERT_SEAT_ID_NOT_NULL);
    }

    @Test
    @DisplayName("콘서트 좌석 예약 Command 생성 실패 - userId가 null인 경우")
    void shouldThrowExceptionWhenUserIdIsNull() {
      // given
      final Long concertSeatId = 1L;
      final Long userId = null;
      final ReserveConcertSeatCommand command = new ReserveConcertSeatCommand(
          concertSeatId, userId);

      // when
      final Set<ConstraintViolation<ReserveConcertSeatCommand>> violations = validator.validate(
          command);

      final ConstraintViolation<ReserveConcertSeatCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("id"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(ConcertConstants.USER_ID_NOT_NULL);
    }

    @Test
    @DisplayName("콘서트 좌석 예약 Command 생성 성공")
    void shouldSuccessfullyCreateReserveConcertSeatCommand() {
      // given
      final Long concertSeatId = 1L;
      final Long userId = 1L;
      final ReserveConcertSeatCommand command = new ReserveConcertSeatCommand(
          concertSeatId, userId);

      // when
      final Set<ConstraintViolation<ReserveConcertSeatCommand>> violations = validator.validate(
          command);

      // then
      assertThat(violations).isEmpty();
    }

  }

  @Nested
  @DisplayName("예약 생성 Command")
  class CreateReservationCommandTest {

    @Test
    @DisplayName("예약 생성 Command 생성 실패 - concertSeatId가 null인 경우")
    void shouldThrowExceptionWhenConcertSeatIdIsNull() {
      // given
      final Long concertSeatId = null;
      final Long userId = 1L;
      final CreateReservationCommand command = new CreateReservationCommand(
          concertSeatId, userId);

      // when
      final Set<ConstraintViolation<CreateReservationCommand>> violations = validator.validate(
          command);

      final ConstraintViolation<CreateReservationCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("concertSeatId"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(ConcertConstants.CONCERT_SEAT_ID_NOT_NULL);
    }

    @Test
    @DisplayName("예약 생성 Command 생성 실패 - userId가 null인 경우")
    void shouldThrowExceptionWhenUserIdIsNull() {
      // given
      final Long concertSeatId = 1L;
      final Long userId = null;
      final CreateReservationCommand command = new CreateReservationCommand(
          concertSeatId, userId);

      // when
      final Set<ConstraintViolation<CreateReservationCommand>> violations = validator.validate(
          command);

      final ConstraintViolation<CreateReservationCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("userId"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(ConcertConstants.USER_ID_NOT_NULL);
    }

    @Test
    @DisplayName("예약 생성 Command 생성 성공")
    void shouldSuccessfullyCreateCreateReservationCommand() {
      // given
      final Long concertSeatId = 1L;
      final Long userId = 1L;
      final CreateReservationCommand command = new CreateReservationCommand(
          concertSeatId, userId);

      // when
      final Set<ConstraintViolation<CreateReservationCommand>> violations = validator.validate(
          command);

      // then
      assertThat(violations).isEmpty();
    }

  }
}