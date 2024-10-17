package com.example.hhplus.concert.infra.db.waitingqueue.impl;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueErrorCode;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueRepository;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.CountWaitingQueueByConcertIdAndStatusParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.FindAllWaitingQueuesByConcertIdAndStatusWithLimitAndLockParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.FindDistinctConcertIdsByStatusParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByIdParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByUuidParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByUuidWithLockParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueuePositionByIdAndConcertIdParam;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
import com.example.hhplus.concert.infra.db.waitingqueue.WaitingQueueJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WaitingQueueRepositoryImpl implements WaitingQueueRepository {

  private final WaitingQueueJpaRepository waitingQueueJpaRepository;

  @Override
  public WaitingQueue save(WaitingQueue waitingQueue) {
    return waitingQueueJpaRepository.save(waitingQueue);
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
    return waitingQueueJpaRepository.findById(param.id()).orElseThrow(() -> new BusinessException(
        WaitingQueueErrorCode.WAITING_QUEUE_NOT_FOUND));
  }

  @Override
  public WaitingQueue getWaitingQueue(GetWaitingQueueByUuidParam param) {
    return waitingQueueJpaRepository.findByUuid(param.uuid())
        .orElseThrow(() -> new BusinessException(
            WaitingQueueErrorCode.WAITING_QUEUE_NOT_FOUND));
  }

  @Override
  public WaitingQueue getWaitingQueue(GetWaitingQueueByUuidWithLockParam param) {
    return waitingQueueJpaRepository.findByUuid(param.uuid())
        .orElseThrow(() -> new BusinessException(
            WaitingQueueErrorCode.WAITING_QUEUE_NOT_FOUND));
  }

  @Override
  public Integer getWaitingQueuePosition(GetWaitingQueuePositionByIdAndConcertIdParam param) {
    return waitingQueueJpaRepository.countByStatusAndConcertIdAndIdLessThan(
        WaitingQueueStatus.WAITING, param.concertId(), param.id());
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
