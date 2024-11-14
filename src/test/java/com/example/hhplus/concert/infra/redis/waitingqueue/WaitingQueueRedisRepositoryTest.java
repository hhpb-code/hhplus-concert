package com.example.hhplus.concert.infra.redis.waitingqueue;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueRepository;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetActiveTokenByUuid;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.ActivateWaitingQueuesParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueuePositionByUuidParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.RemoveActiveTokenParam;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("WaitingQueueRedisRepository 테스트")
class WaitingQueueRedisRepositoryTest {

  @Autowired
  private WaitingQueueRepository target;

  @Autowired
  private RedisTemplate redisTemplate;

  @Value("${queue.waiting-key}")
  private String waitingQueueKey;
  @Value("${queue.active-key}")
  private String activeQueueKey;

  @BeforeEach
  void setUp() {
    redisTemplate.keys("*").forEach(redisTemplate::delete);
  }


  @Test
  @DisplayName("대기열 추가")
  void shouldSuccessfullyAddWaitingQueue() {
    // given
    final String uuid = UUID.randomUUID().toString();

    // when
    final String result = target.addWaitingQueue(uuid);

    // then
    assertThat(result).isEqualTo(uuid);
  }

  @Test
  @DisplayName("대기열 활성화")
  void shouldSuccessfullyActivateWaitingQueues() {
    // given
    final String uuid = UUID.randomUUID().toString();
    redisTemplate.opsForZSet().add(waitingQueueKey, uuid, 1);
    final ActivateWaitingQueuesParam param = new ActivateWaitingQueuesParam(1, 1, TimeUnit.MINUTES);

    // when
    target.activateWaitingQueues(param);

    // then
    final Long waitingQueueSize = redisTemplate.opsForZSet().size(waitingQueueKey);
    final Double score = redisTemplate.opsForZSet().score(activeQueueKey, uuid);
    assertThat(waitingQueueSize).isZero();
    assertThat(score).isNotNull();
  }

  @Test
  @DisplayName("대기열 순번 조회")
  void shouldSuccessfullyGetWaitingQueuePosition() {
    // given
    final String uuid = UUID.randomUUID().toString();
    redisTemplate.opsForZSet().add(waitingQueueKey, uuid, LocalDateTime.now().toEpochSecond(
        ZoneOffset.UTC));
    final GetWaitingQueuePositionByUuidParam param = new GetWaitingQueuePositionByUuidParam(uuid);

    // when
    final Long result = target.getWaitingQueuePosition(param);

    // then
    assertThat(result).isOne();
  }

  @Test
  @DisplayName("활성 토큰 조회")
  void shouldSuccessfullyGetActiveToken() {
    // given
    final String uuid = UUID.randomUUID().toString();
    redisTemplate.opsForZSet().add(activeQueueKey, uuid, 1);
    final GetActiveTokenByUuid param = new GetActiveTokenByUuid(uuid);

    // when
    final WaitingQueue result = target.getActiveToken(param);

    // then
    assertThat(result.getUuid()).isEqualTo(uuid);
  }

  @Test
  @DisplayName("활성 토큰 전체 조회")
  void shouldSuccessfullyGetActiveTokens() {
    // given
    final String uuid1 = UUID.randomUUID().toString();
    final String uuid2 = UUID.randomUUID().toString();
    final String uuid3 = UUID.randomUUID().toString();
    redisTemplate.opsForZSet().add(activeQueueKey, uuid1, 1);
    redisTemplate.opsForZSet().add(activeQueueKey, uuid2, 2);
    redisTemplate.opsForZSet().add(activeQueueKey, uuid3, 3);

    // when
    final List<WaitingQueue> result = target.getAllActiveTokens();

    // then
    assertThat(result).extracting(WaitingQueue::getUuid)
        .containsExactlyInAnyOrder(uuid1, uuid2, uuid3);
  }

  @Test
  @DisplayName("활성 토큰 삭제")
  void shouldSuccessfullyRemoveActiveToken() {
    // given
    final String uuid = UUID.randomUUID().toString();
    final RemoveActiveTokenParam param = new RemoveActiveTokenParam(uuid);
    redisTemplate.opsForZSet().add(activeQueueKey, uuid, 1);

    // when
    target.removeActiveToken(param);

    // then
    final Long result = redisTemplate.opsForZSet().size(activeQueueKey);
    assertThat(result).isZero();
  }
}