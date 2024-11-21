package com.example.hhplus.concert.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hhplus.concert.domain.event.model.OutboxEvent;
import com.example.hhplus.concert.domain.event.model.OutboxEventStatus;
import com.example.hhplus.concert.domain.payment.event.PaymentSuccessEvent;
import com.example.hhplus.concert.domain.support.Event;
import com.example.hhplus.concert.infra.db.event.OutboxEventJpaRepository;
import com.example.hhplus.concert.interfaces.consumer.KafkaConsumer;
import com.example.hhplus.concert.interfaces.consumer.PaymentKafkaConsumer;
import com.example.hhplus.concert.kafka.TestEvent;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.awaitility.Awaitility;
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

  @Autowired
  private PaymentKafkaConsumer paymentKafkaConsumer;

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
    @DisplayName("outbox 이벤트 발행 성공 - test topic 이벤트 발행")
    void shouldPublishOutboxEvent() {
      // given
      LocalDateTime now = LocalDateTime.now();
      final Event event = new TestEvent(now.toString());
      final Long eventId = outboxEventJpaRepository.save(
          OutboxEvent.builder()
              .status(OutboxEventStatus.PENDING)
              .type(event.getType())
              .payload(event.getPayload())
              .build()
      ).getId();

      // when
      target.publishOutboxEvent();

      // then
      final OutboxEvent result = outboxEventJpaRepository.findById(eventId).orElseThrow();
      assertThat(result.getStatus()).isEqualTo(OutboxEventStatus.PUBLISHED);

      Awaitility.await()
          .atMost(5, TimeUnit.SECONDS)
          .untilAsserted(
              () -> assertThat(kafkaConsumer.getMessage()).isEqualTo(event.getPayload()));
    }

    @Test
    @DisplayName("outbox 이벤트 발행 성공 - payment success 이벤트 발행")
    void shouldPublishOutboxEventPaymentSuccess() {
      // given
      Random random = new Random();
      long randomLong = random.nextLong();
      final Event event = new PaymentSuccessEvent(randomLong);
      final Long eventId = outboxEventJpaRepository.save(
          OutboxEvent.builder()
              .status(OutboxEventStatus.PENDING)
              .type(event.getType())
              .payload(event.getPayload())
              .build()
      ).getId();

      // when
      target.publishOutboxEvent();

      // then
      final OutboxEvent result = outboxEventJpaRepository.findById(eventId).orElseThrow();
      assertThat(result.getStatus()).isEqualTo(OutboxEventStatus.PUBLISHED);

      Awaitility.await()
          .atMost(5, TimeUnit.SECONDS)
          .untilAsserted(
              () -> assertThat(paymentKafkaConsumer.getMessage()).isEqualTo(event.getPayload()));
    }

  }

}