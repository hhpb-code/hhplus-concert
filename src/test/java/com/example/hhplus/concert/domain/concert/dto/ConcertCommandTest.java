package com.example.hhplus.concert.domain.concert.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hhplus.concert.domain.concert.ConcertConstants;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.CancelReservationsByIdsCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.ConfirmReservationCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.CreateReservationCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.ReleaseConcertSeatsByIdsCommand;
import com.example.hhplus.concert.domain.concert.dto.ConcertCommand.ReserveConcertSeatCommand;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
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
      final ReserveConcertSeatCommand command = new ReserveConcertSeatCommand(
          concertSeatId);

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
    @DisplayName("콘서트 좌석 예약 Command 생성 성공")
    void shouldSuccessfullyCreateReserveConcertSeatCommand() {
      // given
      final Long concertSeatId = 1L;
      final ReserveConcertSeatCommand command = new ReserveConcertSeatCommand(
          concertSeatId);

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

  @Nested
  @DisplayName("예약 내역 확정 Command")
  class ConfirmReservationCommandTest {

    @Test
    @DisplayName("예약 내역 확정 Command 생성 실패 - reservationId가 null인 경우")
    void shouldThrowExceptionWhenReservationIdIsNull() {
      // given
      final Long reservationId = null;
      final ConfirmReservationCommand command = new ConfirmReservationCommand(
          reservationId);

      // when
      final Set<ConstraintViolation<ConfirmReservationCommand>> violations = validator.validate(
          command);

      final ConstraintViolation<ConfirmReservationCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("reservationId"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(
          ConcertConstants.RESERVATION_ID_MUST_NOT_BE_NULL);
    }

    @Test
    @DisplayName("예약 내역 확정 Command 생성 성공")
    void shouldSuccessfullyCreateConfirmReservationCommand() {
      // given
      final Long reservationId = 1L;
      final ConfirmReservationCommand command = new ConfirmReservationCommand(
          reservationId);

      // when
      final Set<ConstraintViolation<ConfirmReservationCommand>> violations = validator.validate(
          command);

      // then
      assertThat(violations).isEmpty();
    }

  }

  @Nested
  @DisplayName("예약 만료 Command")
  class CancelReservationsByIdsCommandTest {

    @Test
    @DisplayName("예약 만료 Command 생성 실패 - reservationIds가 null인 경우")
    void shouldThrowExceptionWhenReservationIdsIsNull() {
      // given
      final List<Long> reservationIds = null;
      final CancelReservationsByIdsCommand command = new CancelReservationsByIdsCommand(
          reservationIds);

      // when
      final Set<ConstraintViolation<CancelReservationsByIdsCommand>> violations = validator
          .validate(command);

      final ConstraintViolation<CancelReservationsByIdsCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("reservationIds"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(
          ConcertConstants.RESERVATION_IDS_MUST_NOT_BE_NULL);
    }

    @Test
    @DisplayName("예약 만료 Command 생성 실패 - reservationIds가 비어있는 경우")
    void shouldThrowExceptionWhenReservationIdsIsEmpty() {
      // given
      final List<Long> reservationIds = List.of();
      final CancelReservationsByIdsCommand command = new CancelReservationsByIdsCommand(
          reservationIds);

      // when
      final Set<ConstraintViolation<CancelReservationsByIdsCommand>> violations = validator
          .validate(command);

      final ConstraintViolation<CancelReservationsByIdsCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("reservationIds"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(
          ConcertConstants.RESERVATION_IDS_MUST_NOT_BE_NULL);
    }

    @Test
    @DisplayName("예약 만료 Command 생성 성공")
    void shouldSuccessfullyCreateCancelReservationsByIdsCommand() {
      // given
      final List<Long> reservationIds = List.of(1L, 2L, 3L);
      final CancelReservationsByIdsCommand command = new CancelReservationsByIdsCommand(
          reservationIds);

      // when
      final Set<ConstraintViolation<CancelReservationsByIdsCommand>> violations = validator
          .validate(command);

      // then
      assertThat(violations).isEmpty();
    }

  }

  @Nested
  @DisplayName("콘서트 좌석 해제 Command")
  class ReleaseConcertSeatsByIdsCommandTest {

    @Test
    @DisplayName("콘서트 좌석 해제 Command 생성 실패 - concertSeatIds가 null인 경우")
    void shouldThrowExceptionWhenConcertSeatIdsIsNull() {
      // given
      final List<Long> concertSeatIds = null;
      final ReleaseConcertSeatsByIdsCommand command = new ReleaseConcertSeatsByIdsCommand(
          concertSeatIds);

      // when
      final Set<ConstraintViolation<ReleaseConcertSeatsByIdsCommand>> violations = validator
          .validate(command);

      final ConstraintViolation<ReleaseConcertSeatsByIdsCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("concertSeatIds"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(
          ConcertConstants.CONCERT_SEAT_IDS_MUST_NOT_BE_NULL);
    }

    @Test
    @DisplayName("콘서트 좌석 해제 Command 생성 실패 - concertSeatIds가 비어있는 경우")
    void shouldThrowExceptionWhenConcertSeatIdsIsEmpty() {
      // given
      final List<Long> concertSeatIds = List.of();
      final ReleaseConcertSeatsByIdsCommand command = new ReleaseConcertSeatsByIdsCommand(
          concertSeatIds);

      // when
      final Set<ConstraintViolation<ReleaseConcertSeatsByIdsCommand>> violations = validator
          .validate(command);

      final ConstraintViolation<ReleaseConcertSeatsByIdsCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("concertSeatIds"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(
          ConcertConstants.CONCERT_SEAT_IDS_MUST_NOT_BE_NULL);
    }

    @Test
    @DisplayName("콘서트 좌석 해제 Command 생성 성공")
    void shouldSuccessfullyCreateReleaseConcertSeatsByIdsCommand() {
      // given
      final List<Long> concertSeatIds = List.of(1L, 2L, 3L);
      final ReleaseConcertSeatsByIdsCommand command = new ReleaseConcertSeatsByIdsCommand(
          concertSeatIds);

      // when
      final Set<ConstraintViolation<ReleaseConcertSeatsByIdsCommand>> violations = validator
          .validate(command);

      // then
      assertThat(violations).isEmpty();
    }

  }
}