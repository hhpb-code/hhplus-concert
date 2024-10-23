package com.example.hhplus.concert.domain.waitingqueue.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueCommand.ActivateWaitingQueuesCommand;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueCommand.CreateWaitingQueueCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("대기열 Command 단위 테스트")
class WaitingQueueCommandTest {

  @Nested
  @DisplayName("대기열 생성 Command")
  class CreateWaitingQueueCommandTest {

    @Test
    @DisplayName("대기열 생성 Command 실패 - 콘서트 ID가 null인 경우")
    void shouldThrowExceptionWhenConcertIdIsNull() {
      // given
      final Long concertId = null;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new CreateWaitingQueueCommand(concertId));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.Concert.CONCERT_ID_MUST_NOT_BE_NULL.getMessage());
    }

    @Test
    @DisplayName("대기열 생성 Command 성공")
    void shouldSuccessfullyValidateCreateWaitingQueueCommand() {
      // given
      final Long concertId = 1L;

      // when
      final CreateWaitingQueueCommand command = new CreateWaitingQueueCommand(concertId);

      // then
      assertThat(command).isNotNull();
      assertThat(command.concertId()).isEqualTo(concertId);
    }
  }

  @Nested
  @DisplayName("대기열 활성화 Command")
  class ActivateWaitingQueuesCommandTest {

    @Test
    @DisplayName("대기열 활성화 Command 실패 - 콘서트 ID가 null인 경우")
    void shouldThrowExceptionWhenConcertIdIsNull() {
      // given
      final Long concertId = null;
      final Integer availableSlots = 1;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new ActivateWaitingQueuesCommand(concertId, availableSlots));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.Concert.CONCERT_ID_MUST_NOT_BE_NULL.getMessage());
    }

    @Test
    @DisplayName("대기열 활성화 Command 실패 - 사용 가능한 슬롯이 null인 경우")
    void shouldThrowExceptionWhenAvailableSlotsIsNull() {
      // given
      final Long concertId = 1L;
      final Integer availableSlots = null;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new ActivateWaitingQueuesCommand(concertId, availableSlots));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.WaitingQueue.AVAILABLE_SLOTS_MUST_NOT_BE_NULL.getMessage());
    }

    @Test
    @DisplayName("대기열 활성화 Command 실패 - 사용 가능한 슬롯이 0인 경우")
    void shouldThrowExceptionWhenAvailableSlotsIsZero() {
      // given
      final Long concertId = 1L;
      final Integer availableSlots = 0;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new ActivateWaitingQueuesCommand(concertId, availableSlots));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.WaitingQueue.AVAILABLE_SLOTS_MUST_BE_POSITIVE.getMessage());
    }

    @Test
    @DisplayName("대기열 활성화 Command 실패 - 사용 가능한 슬롯이 음수인 경우")
    void shouldThrowExceptionWhenAvailableSlotsIsNegative() {
      // given
      final Long concertId = 1L;
      final Integer availableSlots = -1;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new ActivateWaitingQueuesCommand(concertId, availableSlots));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.WaitingQueue.AVAILABLE_SLOTS_MUST_BE_POSITIVE.getMessage());
    }

    @Test
    @DisplayName("대기열 활성화 Command 성공")
    void shouldSuccessfullyValidateActivateWaitingQueuesCommand() {
      // given
      final Long concertId = 1L;
      final Integer availableSlots = 1;

      // when
      final ActivateWaitingQueuesCommand command = new ActivateWaitingQueuesCommand(concertId,
          availableSlots);

      // then
      assertThat(command).isNotNull();
      assertThat(command.concertId()).isEqualTo(concertId);
      assertThat(command.availableSlots()).isEqualTo(availableSlots);
    }
  }
}
