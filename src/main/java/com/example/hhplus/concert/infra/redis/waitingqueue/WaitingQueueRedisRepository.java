package com.example.hhplus.concert.infra.redis.waitingqueue;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueRepository;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetActiveTokenByUuid;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.ActivateWaitingQueuesParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueuePositionByUuidParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.RemoveActiveTokenParam;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WaitingQueueRedisRepository implements WaitingQueueRepository {

  private final RedisTemplate<String, Object> redisTemplate;

  @Value("${queue.waiting-key}")
  private String waitingQueueKey;

  @Value("${queue.active-key}")
  private String activeQueueKey;

  @Override
  public String addWaitingQueue(String uuid) {
    boolean added = redisTemplate.opsForZSet()
        .add(waitingQueueKey, uuid, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
    return added ? uuid : null;
  }

  @Override
  public void activateWaitingQueues(ActivateWaitingQueuesParam param) {
    redisTemplate.opsForZSet()
        .range(waitingQueueKey, 0, param.availableSlots() - 1)
        .forEach(uuid -> {
          long expirationTimestamp = LocalDateTime.now()
              .plus(param.timeout(), param.unit().toChronoUnit())
              .toEpochSecond(ZoneOffset.UTC);

          redisTemplate.opsForZSet().add(activeQueueKey, uuid, expirationTimestamp);

          redisTemplate.opsForZSet().remove(waitingQueueKey, uuid);
        });
  }

  @Override
  public Long getWaitingQueuePosition(GetWaitingQueuePositionByUuidParam param) {
    Long rank = redisTemplate.opsForZSet().rank(waitingQueueKey, param.uuid());
    if (rank == null) {

      if (redisTemplate.opsForZSet().rank(activeQueueKey, param.uuid()) != null) {
        return 0L;
      }

      throw new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_NOT_FOUND);
    }
    return rank + 1;
  }

  @Override
  public WaitingQueue getActiveToken(GetActiveTokenByUuid param) {
    Double score = redisTemplate.opsForZSet().score(activeQueueKey, param.uuid());

    if (score == null) {
      throw new CoreException(ErrorType.WaitingQueue.ACTIVE_QUEUE_NOT_FOUND);
    }

    return WaitingQueue.builder()
        .uuid(param.uuid())
        .expiredAt(LocalDateTime.ofEpochSecond(score.longValue(), 0, ZoneOffset.UTC))
        .build();
  }

  @Override
  public List<WaitingQueue> getAllActiveTokens() {
    return redisTemplate.opsForZSet()
        .range(activeQueueKey, 0, -1)
        .stream()
        .map(uuid -> WaitingQueue.builder()
            .uuid(uuid.toString())
            .expiredAt(LocalDateTime.ofEpochSecond(
                redisTemplate.opsForZSet().score(activeQueueKey, uuid).longValue(), 0,
                ZoneOffset.UTC))
            .build())
        .toList();
  }

  @Override
  public void removeActiveToken(RemoveActiveTokenParam param) {
    redisTemplate.opsForZSet().remove(activeQueueKey, param.uuid());
  }
}
