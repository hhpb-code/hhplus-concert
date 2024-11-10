package com.example.hhplus.concert.infra.redis.waitingqueue;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueConstants;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.FindAllWaitingQueuesByStatusWithLimitAndLockParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.FindDistinctConcertIdsByStatusParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByUuidParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueuePositionByUuidAndConcertIdParam;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
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
@DisplayName("WaitingQueueRedisRepository 테스트")
class WaitingQueueRedisRepositoryTest {

  @Autowired
  private WaitingQueueRedisRepository target;

  @Autowired
  private RedisTemplate redisTemplate;

  @BeforeEach
  void setUp() {
    redisTemplate.keys("*").forEach(redisTemplate::delete);
  }

  @Nested
  @DisplayName("save 테스트")
  class saveTest {

    @Test
    @DisplayName("WaitingQueue 추가 - waiting 상태")
    void shouldSuccessfullySaveWaitingQueue() {
      // given
      final WaitingQueue waitingQueue = WaitingQueue.builder()
          .concertId(1L)
          .uuid("uuid")
          .status(WaitingQueueStatus.WAITING)
          .createdAt(LocalDateTime.now())
          .build();

      // when
      final WaitingQueue result = target.save(waitingQueue);

      // then
      assertThat(result).isNotNull();
      assertThat(result.getConcertId()).isEqualTo(waitingQueue.getConcertId());
      assertThat(result.getUuid()).isEqualTo(waitingQueue.getUuid());
      assertThat(result.getStatus()).isEqualTo(waitingQueue.getStatus());
      assertThat(result.getCreatedAt()).isEqualTo(waitingQueue.getCreatedAt());
    }

    @Test
    @DisplayName("WaitingQueue 추가 - processing 상태")
    void shouldSuccessfullySaveProcessingQueue() {
      // given
      final WaitingQueue waitingQueue = WaitingQueue.builder()
          .concertId(1L)
          .uuid("uuid")
          .status(WaitingQueueStatus.PROCESSING)
          .createdAt(LocalDateTime.now())
          .build();

      // when
      final WaitingQueue result = target.save(waitingQueue);

      // then
      assertThat(result).isNotNull();
      assertThat(result.getConcertId()).isEqualTo(waitingQueue.getConcertId());
      assertThat(result.getUuid()).isEqualTo(waitingQueue.getUuid());
      assertThat(result.getStatus()).isEqualTo(waitingQueue.getStatus());
      assertThat(result.getCreatedAt()).isEqualTo(waitingQueue.getCreatedAt());
    }

    @Test
    @DisplayName("WaitingQueue 수정 - processing 상태")
    void shouldSuccessfullyUpdateProcessingQueue() {
      // given
      final WaitingQueue waitingQueue = WaitingQueue.builder()
          .concertId(1L)
          .uuid("uuid")
          .status(WaitingQueueStatus.WAITING)
          .createdAt(LocalDateTime.now())
          .build();

      final WaitingQueue updatedWaitingQueue = WaitingQueue.builder()
          .concertId(1L)
          .uuid("uuid")
          .status(WaitingQueueStatus.PROCESSING)
          .createdAt(LocalDateTime.now())
          .expiredAt(LocalDateTime.now().plusMinutes(10))
          .build();

      // when
      target.save(waitingQueue);
      final WaitingQueue result = target.save(updatedWaitingQueue);

      // then
      assertThat(result).isNotNull();
      assertThat(result.getConcertId()).isEqualTo(updatedWaitingQueue.getConcertId());
      assertThat(result.getUuid()).isEqualTo(updatedWaitingQueue.getUuid());
      assertThat(result.getStatus()).isEqualTo(updatedWaitingQueue.getStatus());
      assertThat(result.getCreatedAt()).isEqualTo(updatedWaitingQueue.getCreatedAt());
    }
  }

  @Nested
  @DisplayName("getWaitingQueue 테스트")
  class getWaitingQueueTest {

    @Test
    @DisplayName("WaitingQueue 조회 - By UUID")
    void shouldSuccessfullyGetWaitingQueueByUuid() {

      final WaitingQueue waitingQueue = target.save(WaitingQueue.builder()
          .concertId(1L)
          .uuid("uuid")
          .status(WaitingQueueStatus.WAITING)
          .createdAt(LocalDateTime.now())
          .build());
      final GetWaitingQueueByUuidParam param = new GetWaitingQueueByUuidParam(
          waitingQueue.getUuid());

      // when
      final WaitingQueue result = target.getWaitingQueue(param);

      // then
      assertThat(result).isNotNull();
      assertThat(result.getConcertId()).isEqualTo(waitingQueue.getConcertId());
      assertThat(result.getUuid()).isEqualTo(waitingQueue.getUuid());
      assertThat(result.getStatus()).isEqualTo(waitingQueue.getStatus());
      assertThat(result.getCreatedAt()).isEqualTo(waitingQueue.getCreatedAt());
    }
  }

  @Nested
  @DisplayName("getWaitingQueuePosition 테스트")
  class getWaitingQueuePositionTest {

    @Test
    @DisplayName("WaitingQueue 조회 - By UUID")
    void shouldSuccessfullyGetWaitingQueuePositionByUuid() {

      target.save(WaitingQueue.builder()
          .concertId(1L)
          .uuid(UUID.randomUUID().toString())
          .status(WaitingQueueStatus.WAITING)
          .createdAt(LocalDateTime.now())
          .build());
      target.save(WaitingQueue.builder()
          .concertId(1L)
          .uuid(UUID.randomUUID().toString())
          .status(WaitingQueueStatus.WAITING)
          .createdAt(LocalDateTime.now())
          .build());
      target.save(WaitingQueue.builder()
          .concertId(1L)
          .uuid(UUID.randomUUID().toString())
          .status(WaitingQueueStatus.WAITING)
          .createdAt(LocalDateTime.now())
          .build());

      final WaitingQueue waitingQueue = target.save(WaitingQueue.builder()
          .concertId(1L)
          .uuid(UUID.randomUUID().toString())
          .status(WaitingQueueStatus.WAITING)
          .createdAt(LocalDateTime.now())
          .build());
      final GetWaitingQueuePositionByUuidAndConcertIdParam param = new GetWaitingQueuePositionByUuidAndConcertIdParam(
          waitingQueue.getUuid(), waitingQueue.getConcertId());

      // when
      final Integer result = target.getWaitingQueuePosition(param);

      // then
      assertThat(result).isEqualTo(4);
    }
  }

  @Nested
  @DisplayName("findAllWaitingQueues 테스트")
  class findAllWaitingQueuesTest {

    @Test
    @DisplayName("WaitingQueue 전체 조회")
    void shouldSuccessfullyGetAllWaitingQueues() {
      // given
      List<WaitingQueue> waitingQueues = List.of(
          WaitingQueue.builder()
              .concertId(1L)
              .uuid(UUID.randomUUID().toString())
              .status(WaitingQueueStatus.WAITING)
              .createdAt(LocalDateTime.now())
              .build(),
          WaitingQueue.builder()
              .concertId(1L)
              .uuid(UUID.randomUUID().toString())
              .status(WaitingQueueStatus.WAITING)
              .createdAt(LocalDateTime.now())
              .build(),
          WaitingQueue.builder()
              .concertId(1L)
              .uuid(UUID.randomUUID().toString())
              .status(WaitingQueueStatus.WAITING)
              .createdAt(LocalDateTime.now())
              .build()
      );
      target.saveAll(waitingQueues);

      // when
      final List<WaitingQueue> result = target.findAllWaitingQueues();

      // then
      assertThat(result).isNotNull();
      assertThat(result.size()).isEqualTo(3);
      for (int i = 0; i < result.size(); i++) {
        final WaitingQueue waitingQueue = result.get(i);
        final WaitingQueue expected = waitingQueues.get(i);
        assertThat(waitingQueue.getConcertId()).isEqualTo(expected.getConcertId());
        assertThat(waitingQueue.getUuid()).isEqualTo(expected.getUuid());
        assertThat(waitingQueue.getStatus()).isEqualTo(expected.getStatus());
        assertThat(waitingQueue.getCreatedAt()).isEqualTo(expected.getCreatedAt());
      }
    }

    @DisplayName("WaitingQueue 전체 조회 - By Status Limit")
    @Test
    void shouldSuccessfullyGetAllWaitingQueuesByStatusLimit() {
      // given
      List<WaitingQueue> waitingQueues = List.of(
          WaitingQueue.builder()
              .concertId(1L)
              .uuid(UUID.randomUUID().toString())
              .status(WaitingQueueStatus.WAITING)
              .createdAt(LocalDateTime.now())
              .build(),
          WaitingQueue.builder()
              .concertId(1L)
              .uuid(UUID.randomUUID().toString())
              .status(WaitingQueueStatus.WAITING)
              .createdAt(LocalDateTime.now())
              .build(),
          WaitingQueue.builder()
              .concertId(1L)
              .uuid(UUID.randomUUID().toString())
              .status(WaitingQueueStatus.WAITING)
              .createdAt(LocalDateTime.now())
              .build()
      );
      target.saveAll(waitingQueues);

      final FindAllWaitingQueuesByStatusWithLimitAndLockParam param = new FindAllWaitingQueuesByStatusWithLimitAndLockParam(
          WaitingQueueStatus.WAITING, WaitingQueueConstants.ADD_PROCESSING_COUNT);

      // when
      final List<WaitingQueue> result = target.findAllWaitingQueues(param);

      // then
      assertThat(result).isNotNull();
      assertThat(result.size()).isEqualTo(3);
      for (int i = 0; i < result.size(); i++) {
        final WaitingQueue waitingQueue = result.get(i);
        final WaitingQueue expected = waitingQueues.get(i);
        assertThat(waitingQueue.getConcertId()).isEqualTo(expected.getConcertId());
        assertThat(waitingQueue.getUuid()).isEqualTo(expected.getUuid());
        assertThat(waitingQueue.getStatus()).isEqualTo(expected.getStatus());
        assertThat(waitingQueue.getCreatedAt()).isEqualTo(expected.getCreatedAt());
      }
    }
  }

  @Nested
  @DisplayName("findDistinctConcertIdByStatus 테스트")
  class findDistinctConcertIdByStatusTest {

    @Test
    @DisplayName("WaitingQueue 상태별 ConcertId 조회")
    void shouldSuccessfullyFindDistinctConcertIdByStatus() {
      // given
      target.save(WaitingQueue.builder()
          .concertId(1L)
          .uuid(UUID.randomUUID().toString())
          .status(WaitingQueueStatus.WAITING)
          .createdAt(LocalDateTime.now())
          .build());
      target.save(WaitingQueue.builder()
          .concertId(2L)
          .uuid(UUID.randomUUID().toString())
          .status(WaitingQueueStatus.WAITING)
          .createdAt(LocalDateTime.now())
          .build());
      target.save(WaitingQueue.builder()
          .concertId(3L)
          .uuid(UUID.randomUUID().toString())
          .status(WaitingQueueStatus.WAITING)
          .createdAt(LocalDateTime.now())
          .build());
      final FindDistinctConcertIdsByStatusParam param = new FindDistinctConcertIdsByStatusParam(
          WaitingQueueStatus.WAITING);

      // when
      final List<Long> result = target.findDistinctConcertIdByStatus(param);

      // then
      assertThat(result).isNotNull();
      assertThat(result.size()).isEqualTo(3);
    }
  }

}