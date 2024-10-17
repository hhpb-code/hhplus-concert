package com.example.hhplus.concert.infra.db.waitingqueue.impl;

import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueRepository;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.CountWaitingQueueByConcertIdAndStatusParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.FindAllWaitingQueuesByConcertIdAndStatusWithLimitAndLockParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.FindDistinctConcertIdsByStatusParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByIdParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByUuidWithLockParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueuePositionByIdAndConcertIdParam;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WaitingQueueRepositoryImpl implements WaitingQueueRepository {

  @Override
  public WaitingQueue save(WaitingQueue waitingQueue) {
    return null;
  }

  @Override
  public List<WaitingQueue> saveAll(List<WaitingQueue> waitingQueues) {
    return List.of();
  }

  @Override
  public void expireWaitingQueues() {

  }

  @Override
  public WaitingQueue getWaitingQueue(GetWaitingQueueByIdParam param) {
    return null;
  }

  @Override
  public WaitingQueue getWaitingQueue(GetWaitingQueueByUuidWithLockParam param) {
    return null;
  }

  @Override
  public Integer getWaitingQueuePosition(GetWaitingQueuePositionByIdAndConcertIdParam param) {
    return 0;
  }

  @Override
  public List<Long> findDistinctConcertIdByStatus(FindDistinctConcertIdsByStatusParam param) {
    return List.of();
  }

  @Override
  public Integer countByConcertIdAndStatus(CountWaitingQueueByConcertIdAndStatusParam param) {
    return 0;
  }

  @Override
  public List<WaitingQueue> findAllWaitingQueues(
      FindAllWaitingQueuesByConcertIdAndStatusWithLimitAndLockParam param) {
    return List.of();
  }
}
