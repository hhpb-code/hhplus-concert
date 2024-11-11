package com.example.hhplus.concert.domain.waitingqueue;

import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueCommand.ActivateWaitingQueuesCommand;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByUuidParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueuePositionByUuidParam;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;

public interface WaitingQueueRepository {

  String addWaitingQueue(String uuid);

  void activateWaitingQueues(ActivateWaitingQueuesCommand command);

  WaitingQueue getWaitingQueue(GetWaitingQueueByUuidParam param);

  Integer getWaitingQueuePosition(GetWaitingQueuePositionByUuidParam param);

}