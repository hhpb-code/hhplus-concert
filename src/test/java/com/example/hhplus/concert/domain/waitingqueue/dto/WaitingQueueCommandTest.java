package com.example.hhplus.concert.domain.waitingqueue.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueCommand.ActivateWaitingQueuesCommand;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueCommand.ExpireActivatedWaitingQueueCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("대기열 Command 단위 테스트")
class WaitingQueueCommandTest {

  @Nested
  @DisplayName("대기열 활성화 Command")
  class ActivateWaitingQueuesCommandTest {

    @Test
    @DisplayName("대기열 활성화 Command 실패 - 사용 가능한 슬롯이 null인 경우")
    void shouldThrowExceptionWhenAvailableSlotsIsNull() {
      // given
      final Integer availableSlots = null;

      // when
      final CoreException exception = assertThrows(CoreException.class,
          () -> new ActivateWaitingQueuesCommand(availableSlots));

      // then
      assertThat(exception.getErrorType()).isEqualTo(
          ErrorType.WaitingQueue.AVAILABLE_SLOTS_MUST_NOT_BE_NULL);
    }

    @Test
    @DisplayName("대기열 활성화 Command 실패 - 사용 가능한 슬롯이 0인 경우")
    void shouldThrowExceptionWhenAvailableSlotsIsZero() {
      // given
      final Integer availableSlots = 0;

      // when
      final CoreException exception = assertThrows(CoreException.class,
          () -> new ActivateWaitingQueuesCommand(availableSlots));

      // then
      assertThat(exception.getErrorType()).isEqualTo(
          ErrorType.WaitingQueue.AVAILABLE_SLOTS_MUST_BE_POSITIVE);
    }

    @Test
    @DisplayName("대기열 활성화 Command 실패 - 사용 가능한 슬롯이 음수인 경우")
    void shouldThrowExceptionWhenAvailableSlotsIsNegative() {
      // given
      final Integer availableSlots = -1;

      // when
      final CoreException exception = assertThrows(CoreException.class,
          () -> new ActivateWaitingQueuesCommand(availableSlots));

      // then
      assertThat(exception.getErrorType()).isEqualTo(
          ErrorType.WaitingQueue.AVAILABLE_SLOTS_MUST_BE_POSITIVE);
    }

    @Test
    @DisplayName("대기열 활성화 Command 성공")
    void shouldSuccessfullyValidateActivateWaitingQueuesCommand() {
      // given
      final Integer availableSlots = 1;

      // when
      final ActivateWaitingQueuesCommand command = new ActivateWaitingQueuesCommand(
          availableSlots);

      // then
      assertThat(command).isNotNull();
      assertThat(command.availableSlots()).isEqualTo(availableSlots);
    }
  }

  @Nested
  @DisplayName("활성 대기열 만료 Command")
  class ExpireActivatedWaitingQueueTest {

    @Test
    @DisplayName("활성 대기열 만료 Command 실패 - UUID가 null인 경우")
    void shouldThrowExceptionWhenUuidIsNull() {
      // given
      final String uuid = null;

      // when
      final CoreException exception = assertThrows(CoreException.class,
          () -> new ExpireActivatedWaitingQueueCommand(uuid));

      // then
      assertThat(exception.getErrorType()).isEqualTo(
          ErrorType.WaitingQueue.UUID_MUST_NOT_BE_NULL);
    }

    @Test
    @DisplayName("활성 대기열 만료 Command 성공")
    void shouldSuccessfullyValidateExpireActivatedWaitingQueueCommand() {
      // given
      final String uuid = "uuid";

      // when
      final ExpireActivatedWaitingQueueCommand command = new ExpireActivatedWaitingQueueCommand(
          uuid);

      // then
      assertThat(command).isNotNull();
      assertThat(command.uuid()).isEqualTo(uuid);
    }

  }
}
