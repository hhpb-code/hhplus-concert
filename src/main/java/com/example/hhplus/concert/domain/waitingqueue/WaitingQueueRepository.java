package com.example.hhplus.concert.domain.waitingqueue;

import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.FindAllWaitingQueuesByStatusWithLimitAndLockParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.FindDistinctConcertIdsByStatusParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByUuidParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueuePositionByUuidAndConcertIdParam;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import java.util.List;

public interface WaitingQueueRepository {

  WaitingQueue save(WaitingQueue waitingQueue);

  void saveAll(List<WaitingQueue> waitingQueues);

  WaitingQueue getWaitingQueue(GetWaitingQueueByUuidParam param);

  Integer getWaitingQueuePosition(GetWaitingQueuePositionByUuidAndConcertIdParam param);

  List<Long> findDistinctConcertIdByStatus(FindDistinctConcertIdsByStatusParam param);

  List<WaitingQueue> findAllWaitingQueues();

  List<WaitingQueue> findAllWaitingQueues(
      FindAllWaitingQueuesByStatusWithLimitAndLockParam param);

}