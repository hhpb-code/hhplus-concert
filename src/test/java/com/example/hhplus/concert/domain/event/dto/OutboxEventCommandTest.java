package com.example.hhplus.concert.domain.event.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.event.dto.OutboxEventCommand.CreateOutboxEventCommand;
import com.example.hhplus.concert.domain.payment.event.PaymentSuccessEvent;
import com.example.hhplus.concert.domain.support.Event;
import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("OutboxEventCommand 테스트")
class OutboxEventCommandTest {

  @Nested
  @DisplayName("CreateOutboxEventCommand 테스트")
  class CreateOutboxEventCommandTest {

    @DisplayName("CreateOutboxEventCommand 생성 실패 - event가 null인 경우")
    @Test
    void shouldThrowExceptionWhenEventIsNull() {
      // given
      final Event event = null;

      // when
      final CoreException exception = assertThrows(CoreException.class,
          () -> new CreateOutboxEventCommand(event));

      // then
      assertThat(exception.getErrorType()).isEqualTo(
          ErrorType.OutboxEvent.OUTBOX_EVENT_MUST_NOT_BE_NULL);
    }

    @DisplayName("CreateOutboxEventCommand 생성")
    @Test
    void create() {
      // given
      final Event event = new PaymentSuccessEvent(1L);

      // when
      final var result = new CreateOutboxEventCommand(event);

      // then
      assertThat(result).isNotNull();
    }

  }

  @Nested
  @DisplayName("PublishOutboxEventCommand 테스트")
  class PublishOutboxEventCommandTest {

    @DisplayName("PublishOutboxEventCommand 생성 실패 - event가 null인 경우")
    @Test
    void shouldThrowExceptionWhenEventIsNull() {
      // given
      final Event event = null;

      // when
      final CoreException exception = assertThrows(CoreException.class,
          () -> new CreateOutboxEventCommand(event));

      // then
      assertThat(exception.getErrorType()).isEqualTo(
          ErrorType.OutboxEvent.OUTBOX_EVENT_MUST_NOT_BE_NULL);
    }

    @DisplayName("PublishOutboxEventCommand 생성")
    @Test
    void create() {
      // given
      final Event event = new PaymentSuccessEvent(1L);

      // when
      final var result = new CreateOutboxEventCommand(event);

      // then
      assertThat(result).isNotNull();
    }

  }

}