package com.example.hhplus.concert.domain.event.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
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


}