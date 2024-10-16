package com.example.hhplus.concert.domain.waitingqueue;

import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.CountWaitingQueueByConcertIdAndStatusParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.FindAllWaitingQueuesByConcertIdAndStatusWithLimitAndLockParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.FindDistinctConcertIdsByStatusParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByIdParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByUuidParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByUuidWithLockParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueuePositionByIdAndConcertIdParam;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import java.util.List;

public interface WaitingQueueRepository {

  WaitingQueue save(WaitingQueue waitingQueue);

  List<WaitingQueue> saveAll(List<WaitingQueue> waitingQueues);

  void expireWaitingQueues();

  WaitingQueue getWaitingQueue(GetWaitingQueueByIdParam param);

  WaitingQueue getWaitingQueue(GetWaitingQueueByUuidParam param);

  WaitingQueue getWaitingQueue(GetWaitingQueueByUuidWithLockParam param);

  Integer getWaitingQueuePosition(GetWaitingQueuePositionByIdAndConcertIdParam param);

  List<Long> findDistinctConcertIdByStatus(FindDistinctConcertIdsByStatusParam param);

  Integer countByConcertIdAndStatus(CountWaitingQueueByConcertIdAndStatusParam param);

  List<WaitingQueue> findAllWaitingQueues(
      FindAllWaitingQueuesByConcertIdAndStatusWithLimitAndLockParam param);

}
