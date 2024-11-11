package com.example.hhplus.concert.infra.redis.waitingqueue;

import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueRepository;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueCommand.ActivateWaitingQueuesCommand;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByUuidParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueuePositionByUuidParam;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WaitingQueueRedisRepository implements WaitingQueueRepository {

  private final RedisTemplate<String, Object> redisTemplate;
  private final RedissonClient redissonClient;
  @Value("${queue.waiting-key}")
  private String waitingQueueKey;
  @Value("${queue.active-key}")
  private String activeQueueKey;

  @Override
  public String addWaitingQueue(String uuid) {
    return "";
  }

  @Override
  public void activateWaitingQueues(ActivateWaitingQueuesCommand command) {

  }

  @Override
  public WaitingQueue getWaitingQueue(GetWaitingQueueByUuidParam param) {
    return null;
  }

  @Override
  public Integer getWaitingQueuePosition(GetWaitingQueuePositionByUuidParam param) {
    return 0;
  }
  
}
