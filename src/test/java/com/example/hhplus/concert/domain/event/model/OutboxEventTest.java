package com.example.hhplus.concert.domain.event.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.event.EventConstants;
import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("OutboxEvent 단위 테스트")
class OutboxEventTest {

  @Nested
  @DisplayName("이벤트 발행")
  class PublishTest {

    @Test
    @DisplayName("이벤트 발행 실패 - 이미 발행된 이벤트인 경우")
    void shouldThrowExceptionWhenEventIsAlreadyPublished() {
      // given
      final OutboxEvent event = OutboxEvent.builder()
          .status(OutboxEventStatus.PUBLISHED)
          .build();

      // when
      final CoreException exception = assertThrows(CoreException.class, () -> event.publish());

      // then
      assertThat(exception.getErrorType()).isEqualTo(
          ErrorType.OutboxEvent.OUTBOX_EVENT_ALREADY_PUBLISHED);
    }

    @Test
    @DisplayName("이벤트 발행 성공")
    void shouldSuccessfullyPublishEvent() {
      // given
      final OutboxEvent event = OutboxEvent.builder()
          .status(OutboxEventStatus.PENDING)
          .build();

      // when
      event.publish();

      // then
      assertThat(event.getStatus()).isEqualTo(OutboxEventStatus.PUBLISHED);
    }
  }

  @Nested
  @DisplayName("이벤트 실패")
  class FailTest {

    @Test
    @DisplayName("이벤트 실패 - 이미 실패한 이벤트인 경우")
    void shouldThrowExceptionWhenEventIsAlreadyFailed() {
      // given
      final OutboxEvent event = OutboxEvent.builder()
          .status(OutboxEventStatus.FAILED)
          .build();

      // when
      final CoreException exception = assertThrows(CoreException.class, () -> event.fail());

      // then
      assertThat(exception.getErrorType()).isEqualTo(
          ErrorType.OutboxEvent.OUTBOX_EVENT_ALREADY_FAILED);
    }

    @Test
    @DisplayName("이벤트 실패 성공")
    void shouldSuccessfullyFailEvent() {
      // given
      final OutboxEvent event = OutboxEvent.builder()
          .status(OutboxEventStatus.PENDING)
          .build();

      // when
      event.fail();

      // then
      assertThat(event.getStatus()).isEqualTo(OutboxEventStatus.FAILED);
    }
  }

  @Nested
  @DisplayName("이벤트 재시도")
  class RetryTest {

    @Test
    @DisplayName("이벤트 재시도 - 이미 실패한 이벤트가 아닌 경우")
    void shouldThrowExceptionWhenEventIsNotFailed() {
      // given
      final OutboxEvent event = OutboxEvent.builder()
          .status(OutboxEventStatus.PENDING)
          .build();

      // when
      final CoreException exception = assertThrows(CoreException.class, event::retry);

      // then
      assertThat(exception.getErrorType()).isEqualTo(
          ErrorType.OutboxEvent.OUTBOX_EVENT_NOT_FAILED);
    }

    @Test
    @DisplayName("이벤트 재시도 - 재시도 횟수 초과")
    void shouldThrowExceptionWhenRetryCountExceeds() {
      // given
      final OutboxEvent event = OutboxEvent.builder()
          .status(OutboxEventStatus.FAILED)
          .retryCount(EventConstants.MAX_RETRY_COUNT)
          .build();

      // when
      final CoreException exception = assertThrows(CoreException.class, event::retry);

      // then
      assertThat(exception.getErrorType()).isEqualTo(
          ErrorType.OutboxEvent.OUTBOX_EVENT_RETRY_EXCEEDED);
    }

    @Test
    @DisplayName("이벤트 재시도 - updateAt이 null인 경우")
    void shouldThrowExceptionWhenUpdatedAtIsNull() {
      // given
      final OutboxEvent event = OutboxEvent.builder()
          .status(OutboxEventStatus.FAILED)
          .retryCount(0)
          .build();

      // when
      final CoreException exception = assertThrows(CoreException.class, event::retry);

      // then
      assertThat(exception.getErrorType()).isEqualTo(
          ErrorType.OutboxEvent.OUTBOX_EVENT_UPDATED_AT_NULL);
    }

    @Test
    @DisplayName("이벤트 재시도 - 재시도 간격 미만")
    void shouldThrowExceptionWhenRetryIntervalNotPassed() {
      // given
      final OutboxEvent event = OutboxEvent.builder()
          .status(OutboxEventStatus.FAILED)
          .retryCount(0)
          .retryAt(LocalDateTime.now().plusSeconds(1))
          .build();

      // when
      final CoreException exception = assertThrows(CoreException.class, event::retry);

      // then
      assertThat(exception.getErrorType()).isEqualTo(
          ErrorType.OutboxEvent.OUTBOX_EVENT_RETRY_INTERVAL_NOT_PASSED);
    }

    @Test
    @DisplayName("이벤트 재시도 성공")
    void shouldSuccessfullyRetryEvent() {
      // given
      final OutboxEvent event = OutboxEvent.builder()
          .status(OutboxEventStatus.FAILED)
          .retryCount(0)
          .retryAt(LocalDateTime.now().minusMinutes(EventConstants.RETRY_INTERVAL_MINUTES))
          .build();

      // when
      event.retry();

      // then
      assertThat(event.getStatus()).isEqualTo(OutboxEventStatus.PENDING);
    }
  }


}