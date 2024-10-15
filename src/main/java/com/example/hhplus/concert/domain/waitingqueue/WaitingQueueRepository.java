package com.example.hhplus.concert.domain.waitingqueue;

import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.GetWaitingQueueByIdParam;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;

public interface WaitingQueueRepository {

  WaitingQueue save(WaitingQueue waitingQueue);

  WaitingQueue getWaitingQueue(GetWaitingQueueByIdParam request);

}
