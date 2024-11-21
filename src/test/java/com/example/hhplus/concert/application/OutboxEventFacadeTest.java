package com.example.hhplus.concert.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hhplus.concert.domain.event.model.OutboxEvent;
import com.example.hhplus.concert.domain.event.model.OutboxEventStatus;
import com.example.hhplus.concert.domain.support.Event;
import com.example.hhplus.concert.infra.db.event.OutboxEventJpaRepository;
import com.example.hhplus.concert.interfaces.consumer.KafkaConsumer;
import com.example.hhplus.concert.kafka.TestEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("OutboxEventFacade 통합 테스트")
class OutboxEventFacadeTest {

  @Autowired
  private OutboxEventFacade target;

  @Autowired
  private OutboxEventJpaRepository outboxEventJpaRepository;

  @Autowired
  private KafkaConsumer kafkaConsumer;

  @BeforeEach
  void setUp() {
    outboxEventJpaRepository.deleteAll();
  }

  @Nested
  @DisplayName("outbox 이벤트 발행 테스트")
  class PublishOutboxEventTest {

    @Test
    @DisplayName("outbox 이벤트 발행 실패 - event가 존재하지 않는 경우")
    void shouldThrowExceptionWhenIdIsNotExist() {
      // given

      // when
      target.publishOutboxEvent();
    }

    @Test
    @DisplayName("outbox 이벤트 발행 성공")
    void shouldPublishOutboxEvent() throws InterruptedException {
      // given
      final Event event = new TestEvent("test");
      final Long eventId = outboxEventJpaRepository.save(
          OutboxEvent.builder()
              .status(OutboxEventStatus.PENDING)
              .type(event.getType())
              .payload(event.getPayload())
              .build()
      ).getId();

      // when
      target.publishOutboxEvent();
      Thread.sleep(4000);

      // then
      final OutboxEvent result = outboxEventJpaRepository.findById(eventId).orElseThrow();
      assertThat(result.getStatus()).isEqualTo(OutboxEventStatus.PUBLISHED);

      assertThat(kafkaConsumer.getMessage()).isEqualTo(event.getPayload());
    }

  }

}