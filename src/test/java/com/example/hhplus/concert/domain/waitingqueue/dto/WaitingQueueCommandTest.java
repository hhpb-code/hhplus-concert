package com.example.hhplus.concert.domain.waitingqueue.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueConstants;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueCommand.ActivateWaitingQueuesCommand;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueCommand.CreateWaitingQueueCommand;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("대기열 command 단위 테스트")
class WaitingQueueCommandTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Nested
  @DisplayName("대기열 생성 command")
  class CreateWaitingQueueCommandTest {

    @Test
    @DisplayName("대기열 생성 command 유효성 검증 실패 - 콘서트 ID가 null")
    void shouldThrowExceptionWhenConcertIdIsNull() {
      // given
      final Long concertId = null;
      final CreateWaitingQueueCommand command = new CreateWaitingQueueCommand(concertId);

      // when
      final Set<ConstraintViolation<CreateWaitingQueueCommand>> violations = validator.validate(
          command);

      final ConstraintViolation<CreateWaitingQueueCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("concertId"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(WaitingQueueConstants.CONCERT_ID_NULL_MESSAGE);
    }

    @Test
    @DisplayName("대기열 생성 command 유효성 검증 성공")
    void shouldSuccessfullyValidateCreateWaitingQueueCommand() {
      // given
      final Long concertId = 1L;
      final CreateWaitingQueueCommand command = new CreateWaitingQueueCommand(concertId);

      // when
      final Set<ConstraintViolation<CreateWaitingQueueCommand>> violations = validator.validate(
          command);

      // then
      assertThat(violations).isEmpty();
    }
  }

  @Nested
  @DisplayName("대기열 활성화 command")
  class ActivateWaitingQueuesCommandTest {

    @Test
    @DisplayName("대기열 활성화 command 유효성 검증 실패 - 콘서트 ID가 null")
    void shouldThrowExceptionWhenConcertIdIsNull() {
      // given
      final Long concertId = null;
      final Integer availableSlots = 1;
      final ActivateWaitingQueuesCommand command = new ActivateWaitingQueuesCommand(concertId,
          availableSlots);

      // when
      final Set<ConstraintViolation<ActivateWaitingQueuesCommand>> violations = validator.validate(
          command);

      final ConstraintViolation<ActivateWaitingQueuesCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("concertId"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(WaitingQueueConstants.CONCERT_ID_NULL_MESSAGE);
    }

    @Test
    @DisplayName("대기열 활성화 command 유효성 검증 실패 - 사용 가능한 슬롯이 null")
    void shouldThrowExceptionWhenAvailableSlotsIsNull() {
      // given
      final Long concertId = 1L;
      final Integer availableSlots = null;
      final ActivateWaitingQueuesCommand command = new ActivateWaitingQueuesCommand(concertId,
          availableSlots);

      // when
      final Set<ConstraintViolation<ActivateWaitingQueuesCommand>> violations = validator.validate(
          command);

      final ConstraintViolation<ActivateWaitingQueuesCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("availableSlots"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(
          WaitingQueueConstants.AVAILABLE_SLOTS_NULL_MESSAGE);
    }

    @Test
    @DisplayName("대기열 활성화 command 유효성 검증 실패 - 사용 가능한 슬롯이 0")
    void shouldThrowExceptionWhenAvailableSlotsIsZero() {
      // given
      final Long concertId = 1L;
      final Integer availableSlots = 0;
      final ActivateWaitingQueuesCommand command = new ActivateWaitingQueuesCommand(concertId,
          availableSlots);

      // when
      final Set<ConstraintViolation<ActivateWaitingQueuesCommand>> violations = validator.validate(
          command);

      final ConstraintViolation<ActivateWaitingQueuesCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("availableSlots"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(
          WaitingQueueConstants.AVAILABLE_SLOTS_NEGATIVE_MESSAGE);
    }

    @Test
    @DisplayName("대기열 활성화 command 유효성 검증 실패 - 사용 가능한 슬롯이 음수")
    void shouldThrowExceptionWhenAvailableSlotsIsNegative() {
      // given
      final Long concertId = 1L;
      final Integer availableSlots = -1;
      final ActivateWaitingQueuesCommand command = new ActivateWaitingQueuesCommand(concertId,
          availableSlots);

      // when
      final Set<ConstraintViolation<ActivateWaitingQueuesCommand>> violations = validator.validate(
          command);

      final ConstraintViolation<ActivateWaitingQueuesCommand> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("availableSlots"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(
          WaitingQueueConstants.AVAILABLE_SLOTS_NEGATIVE_MESSAGE);
    }

    @Test
    @DisplayName("대기열 활성화 command 유효성 검증 성공")
    void shouldSuccessfullyValidateActivateWaitingQueuesCommand() {
      // given
      final Long concertId = 1L;
      final Integer availableSlots = 1;
      final ActivateWaitingQueuesCommand command = new ActivateWaitingQueuesCommand(concertId,
          availableSlots);

      // when
      final Set<ConstraintViolation<ActivateWaitingQueuesCommand>> violations = validator.validate(
          command);

      // then
      assertThat(violations).isEmpty();
    }
  }
}