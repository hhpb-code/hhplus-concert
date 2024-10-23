package com.example.hhplus.concert.infra.db.waitingqueue.impl;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
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
    return waitingQueueJpaRepository.saveAll(waitingQueues);
  }

  @Override
  public void expireWaitingQueues() {
    waitingQueueJpaRepository.expireWaitingQueues();
  }

  @Override
  public WaitingQueue getWaitingQueue(GetWaitingQueueByIdParam param) {
    return waitingQueueJpaRepository.findById(param.id())
        .orElseThrow(() -> new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_NOT_FOUND));
  }

  @Override
  public WaitingQueue getWaitingQueue(GetWaitingQueueByUuidParam param) {
    return waitingQueueJpaRepository.findByUuid(param.uuid())
        .orElseThrow(() -> new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_NOT_FOUND));
  }

  @Override
  public WaitingQueue getWaitingQueue(GetWaitingQueueByUuidWithLockParam param) {
    return waitingQueueJpaRepository.findByUuid(param.uuid())
        .orElseThrow(() -> new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_NOT_FOUND));
  }

  @Override
  public Integer getWaitingQueuePosition(GetWaitingQueuePositionByIdAndConcertIdParam param) {
    return waitingQueueJpaRepository.countByStatusAndConcertIdAndIdLessThan(
        WaitingQueueStatus.WAITING, param.concertId(), param.id());
  }

  @Override
  public List<Long> findDistinctConcertIdByStatus(FindDistinctConcertIdsByStatusParam param) {
    return waitingQueueJpaRepository.findAllDistinctConcertIdsByStatus(param.status());
  }

  @Override
  public Integer countByConcertIdAndStatus(CountWaitingQueueByConcertIdAndStatusParam param) {
    return waitingQueueJpaRepository.countByConcertIdAndStatus(param.concertId(), param.status());
  }

  @Override
  public List<WaitingQueue> findAllWaitingQueues(
      FindAllWaitingQueuesByConcertIdAndStatusWithLimitAndLockParam param) {
    return waitingQueueJpaRepository.findAllByConcertIdAndStatusWithLimitAndLock(
        param.concertId(), param.status(), param.limit());
  }
}
