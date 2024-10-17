package com.example.hhplus.concert.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hhplus.concert.domain.concert.model.Concert;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueConstants;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
import com.example.hhplus.concert.infra.db.concert.ConcertJpaRepository;
import com.example.hhplus.concert.infra.db.waitingqueue.WaitingQueueJpaRepository;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("WaitingQueueFacade 동시성 테스트")
class WaitingQueueFacadeConcurrencyTest {

  @Autowired
  private WaitingQueueFacade waitingQueueFacade;

  @Autowired
  private WaitingQueueJpaRepository waitingQueueJpaRepository;

  @Autowired
  private ConcertJpaRepository concertJpaRepository;

  @BeforeEach
  void setUp() {
    waitingQueueJpaRepository.deleteAll();
    concertJpaRepository.deleteAll();
  }

  @Nested
  @DisplayName("대기열 토큰 동시성 테스트")
  class CreateWaitingQueueToken {

    @Test
    @DisplayName("대기열 토큰 생성 성공")
    void shouldCreateWaitingQueueToken() {
      // given
      final int threadCount = 100;
      final Long concertId = 1L;

      // when
      final List<CompletableFuture<WaitingQueue>> futures = IntStream.range(0, threadCount)
          .mapToObj(i -> CompletableFuture.supplyAsync(
              () -> waitingQueueFacade.createWaitingQueueToken(concertId)))
          .toList();

      CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

      // then
      final List<WaitingQueue> waitingQueues = waitingQueueJpaRepository.findAll();
      waitingQueues.forEach(waitingQueue -> {
        assertThat(waitingQueue).isNotNull();
        assertThat(waitingQueue.getConcertId()).isEqualTo(concertId);
        assertThat(waitingQueue.getExpiredAt()).isNull();
        assertThat(waitingQueue.getUuid()).isNotNull();
        assertThat(waitingQueue.getStatus()).isEqualTo(WaitingQueueStatus.WAITING);
        assertThat(waitingQueue.getCreatedAt()).isNotNull();
        assertThat(waitingQueue.getUpdatedAt()).isNull();
      });
    }

  }

  @Nested
  @DisplayName("대기열 토큰 활성화 동시성 테스트")
  class ActivateWaitingQueues {

    @Test
    @DisplayName("대기열 토큰 활성화 동시성 테스트 성공")
    void shouldActivateWaitingQueues() {
      // given
      final int threadCount = 100;
      final Concert concert = concertJpaRepository.save(
          Concert.builder().title("title").description("description").build());
      final Long concertId = concert.getId();

      final List<WaitingQueue> waitingQueues = IntStream.range(0, threadCount)
          .mapToObj(i -> WaitingQueue.builder()
              .concertId(concertId)
              .uuid(UUID.randomUUID().toString())
              .status(WaitingQueueStatus.WAITING)
              .build())
          .toList();
      waitingQueueJpaRepository.saveAll(waitingQueues);

      // when
      final List<CompletableFuture<Void>> futures = IntStream.range(0, threadCount)
          .mapToObj(i -> CompletableFuture.runAsync(
              () -> waitingQueueFacade.activateWaitingQueues()))
          .toList();

      CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

      // then
      final List<WaitingQueue> result = waitingQueueJpaRepository.findAll();

      for (int i = 0; i < result.size(); i++) {
        final WaitingQueue waitingQueue = result.get(i);

        if (i < WaitingQueueConstants.MAX_PROCESSING_WAITING_QUEUE_COUNT) {
          assertThat(waitingQueue.getExpiredAt()).isNotNull();
          assertThat(waitingQueue.getStatus()).isEqualTo(WaitingQueueStatus.PROCESSING);
        } else {
          assertThat(waitingQueue.getExpiredAt()).isNull();
          assertThat(waitingQueue.getStatus()).isEqualTo(WaitingQueueStatus.WAITING);
        }
      }
    }
  }

}