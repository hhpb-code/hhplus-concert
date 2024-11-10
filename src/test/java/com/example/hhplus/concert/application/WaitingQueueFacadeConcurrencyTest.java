package com.example.hhplus.concert.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
import com.example.hhplus.concert.infra.db.concert.ConcertJpaRepository;
import com.example.hhplus.concert.infra.redis.waitingqueue.WaitingQueueRedisRepository;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("WaitingQueueFacade 동시성 테스트")
class WaitingQueueFacadeConcurrencyTest {

  @Autowired
  private WaitingQueueFacade waitingQueueFacade;

  @Autowired
  private ConcertJpaRepository concertJpaRepository;

  @Autowired
  private WaitingQueueRedisRepository waitingQueueRedisRepository;

  @Autowired
  private RedisTemplate redisTemplate;

  @BeforeEach
  void setUp() {
    concertJpaRepository.deleteAll();

    redisTemplate.keys("*").forEach(redisTemplate::delete);
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
      final List<WaitingQueue> waitingQueues = waitingQueueRedisRepository.findAllWaitingQueues();
      waitingQueues.forEach(waitingQueue -> {
        assertThat(waitingQueue).isNotNull();
        assertThat(waitingQueue.getConcertId()).isEqualTo(concertId);
        assertThat(waitingQueue.getExpiredAt()).isNull();
        assertThat(waitingQueue.getUuid()).isNotNull();
        assertThat(waitingQueue.getStatus()).isEqualTo(WaitingQueueStatus.WAITING);
        assertThat(waitingQueue.getCreatedAt()).isNotNull();
      });
    }

  }

}