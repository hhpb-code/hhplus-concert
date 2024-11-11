package com.example.hhplus.concert.domain.waitingqueue.dto;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;

public class WaitingQueueQuery {

  public record GetWaitingQueuePositionByUuid(String uuid) {

    public GetWaitingQueuePositionByUuid {
      if (uuid == null || uuid.isEmpty()) {
        throw new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_UUID_MUST_NOT_BE_EMPTY);
      }
    }
  }

  public record GetActiveTokenByUuid(String uuid) {

    public GetActiveTokenByUuid {
      if (uuid == null || uuid.isEmpty()) {
        throw new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_UUID_MUST_NOT_BE_EMPTY);
      }
    }
  }


}
