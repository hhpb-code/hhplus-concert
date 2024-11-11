package com.example.hhplus.concert.domain.waitingqueue;

import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetActiveTokenByUuid;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.ActivateWaitingQueuesParam;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueuePositionByUuidParam;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import java.util.List;

public interface WaitingQueueRepository {

  String addWaitingQueue(String uuid);

  void activateWaitingQueues(ActivateWaitingQueuesParam param);

  Long getWaitingQueuePosition(GetWaitingQueuePositionByUuidParam param);

  WaitingQueue getActiveToken(GetActiveTokenByUuid param);

  List<WaitingQueue> getAllActiveTokens();

  void removeActiveToken(String uuid);

}