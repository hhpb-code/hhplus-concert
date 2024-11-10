package com.example.hhplus.concert.infra.redis.waitingqueue;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueRepository;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.FindAllWaitingQueuesByStatusWithLimitAndLockParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.FindDistinctConcertIdsByStatusParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByUuidParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueuePositionByUuidAndConcertIdParam;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WaitingQueueRedisRepository implements WaitingQueueRepository {

  private static final String HASH_SUFFIX = ":hash";
  private static final String ZSET_SUFFIX = ":zset";
  private final RedisTemplate<String, Object> redisTemplate;
  private final RedissonClient redissonClient;
  @Value("${queue.waiting-key}")
  private String waitingQueueKey;
  @Value("${queue.active-key}")
  private String activeQueueKey;

  public WaitingQueue save(WaitingQueue waitingQueue) {
    String hashKey = waitingQueueKey + HASH_SUFFIX;
    String waitingZSetKey = waitingQueueKey + waitingQueue.getConcertId() + ZSET_SUFFIX;
    String activeZSetKey = activeQueueKey + waitingQueue.getConcertId() + ZSET_SUFFIX;
    String lockKey = "lock:" + waitingZSetKey;

    RLock lock = redissonClient.getLock(lockKey);
    try {
      boolean isLocked = lock.tryLock(5, 3, TimeUnit.SECONDS);
      if (!isLocked) {
        throw new CoreException(ErrorType.FAILED_TO_ACQUIRE_LOCK);
      }

      Double currentScore = waitingQueue.getScore();

      double newScore = currentScore == null ? 1 : currentScore;

      if (currentScore == null) {
        Double maxScore = redisTemplate.opsForZSet()
            .reverseRangeWithScores(waitingZSetKey, 0, 0)
            .stream()
            .findFirst()
            .map(TypedTuple::getScore)
            .orElse(0.0);

        newScore = maxScore + 1;
      }

      redisTemplate.opsForHash().put(hashKey, waitingQueue.getUuid(), WaitingQueue.builder()
          .uuid(waitingQueue.getUuid())
          .concertId(waitingQueue.getConcertId())
          .status(waitingQueue.getStatus())
          .score(newScore)
          .createdAt(waitingQueue.getCreatedAt())
          .expiredAt(waitingQueue.getExpiredAt())
          .build()
      );

      if (waitingQueue.getExpiredAt() != null) {
        redisTemplate.expire(hashKey,
            Duration.between(LocalDateTime.now(), waitingQueue.getExpiredAt()));
      }

      if (waitingQueue.getStatus().equals(WaitingQueueStatus.PROCESSING)) {
        if (redisTemplate.opsForZSet().rank(waitingZSetKey, waitingQueue.getUuid()) != null) {
          redisTemplate.opsForZSet().remove(waitingZSetKey, waitingQueue.getUuid());
        }
        redisTemplate.opsForZSet().add(activeZSetKey, waitingQueue.getUuid(), newScore);
      } else if (waitingQueue.getStatus().equals(WaitingQueueStatus.EXPIRED)) {
        if (redisTemplate.opsForZSet().rank(waitingZSetKey, waitingQueue.getUuid()) != null) {
          redisTemplate.opsForZSet().remove(waitingZSetKey, waitingQueue.getUuid());
        }
        if (redisTemplate.opsForZSet().rank(activeZSetKey, waitingQueue.getUuid()) != null) {
          redisTemplate.opsForZSet().remove(activeZSetKey, waitingQueue.getUuid());
        }

      } else {
        if (redisTemplate.opsForZSet().rank(activeZSetKey, waitingQueue.getUuid()) != null) {
          redisTemplate.opsForZSet().remove(activeZSetKey, waitingQueue.getUuid());
        }
        redisTemplate.opsForZSet().add(waitingZSetKey, waitingQueue.getUuid(), newScore);
      }

      return (WaitingQueue) redisTemplate.opsForHash().get(hashKey, waitingQueue.getUuid());
    } catch (InterruptedException e) {
      throw new RuntimeException("Thread was interrupted while attempting to acquire lock", e);
    } catch (Throwable e) {
      throw new RuntimeException("Error while saving WaitingQueue", e);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public void saveAll(List<WaitingQueue> waitingQueues) {
    waitingQueues.forEach(this::save);
  }

  public WaitingQueue getWaitingQueue(GetWaitingQueueByUuidParam param) {
    String hashKey = waitingQueueKey + HASH_SUFFIX;

    WaitingQueue waitingQueue = (WaitingQueue) redisTemplate.opsForHash()
        .get(hashKey, param.uuid());
    if (waitingQueue == null) {
      throw new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_NOT_FOUND);
    }

    return waitingQueue;
  }

  public Integer getWaitingQueuePosition(GetWaitingQueuePositionByUuidAndConcertIdParam param) {
    String waitingZSetKey = waitingQueueKey + param.concertId() + ZSET_SUFFIX;
    String activeZSetKey = activeQueueKey + param.concertId() + ZSET_SUFFIX;

    WaitingQueue waitingQueue = (WaitingQueue) redisTemplate.opsForHash()
        .get(waitingQueueKey + HASH_SUFFIX, param.uuid());

    if (waitingQueue == null) {
      throw new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_NOT_FOUND);
    }

    if (waitingQueue.getStatus() == WaitingQueueStatus.PROCESSING) {
      if (redisTemplate.opsForZSet().rank(activeZSetKey, param.uuid()) == null) {
        throw new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_NOT_FOUND);
      }

      return 0;
    }

    Long position = redisTemplate.opsForZSet().rank(waitingZSetKey, param.uuid());
    if (position == null) {
      throw new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_NOT_FOUND);
    }

    return position.intValue() + 1;
  }

  public List<Long> findDistinctConcertIdByStatus(FindDistinctConcertIdsByStatusParam param) {
    return redisTemplate.opsForHash().values(waitingQueueKey + HASH_SUFFIX).stream()
        .map(WaitingQueue.class::cast)
        .filter(waitingQueue -> waitingQueue.getStatus() == param.status())
        .map(WaitingQueue::getConcertId)
        .distinct()
        .toList();
  }

  @Override
  public List<WaitingQueue> findAllWaitingQueues() {
    return redisTemplate.opsForHash().values(waitingQueueKey + HASH_SUFFIX).stream()
        .map(WaitingQueue.class::cast)
        .sorted(Comparator.comparing(WaitingQueue::getScore))
        .toList();
  }


  @Override
  public List<WaitingQueue> findAllWaitingQueues(
      FindAllWaitingQueuesByStatusWithLimitAndLockParam param) {
    return redisTemplate.opsForHash().values(waitingQueueKey + HASH_SUFFIX).stream()
        .map(WaitingQueue.class::cast)
        .filter(waitingQueue -> waitingQueue.getStatus().equals(param.status()))
        .sorted(Comparator.comparing(WaitingQueue::getScore))
        .limit(param.limit())
        .toList();
  }
}
