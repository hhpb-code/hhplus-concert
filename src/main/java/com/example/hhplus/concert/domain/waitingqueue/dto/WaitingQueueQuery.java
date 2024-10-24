package com.example.hhplus.concert.domain.waitingqueue.dto;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;

public class WaitingQueueQuery {

  public record GetWaitingQueueByIdQuery(Long id) {

    public GetWaitingQueueByIdQuery {
      if (id == null) {
        throw new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_ID_MUST_NOT_BE_NULL);
      }
    }
  }

  public record GetWaitingQueueByUuid(String uuid) {

    public GetWaitingQueueByUuid {
      if (uuid == null || uuid.isEmpty()) {
        throw new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_UUID_MUST_NOT_BE_EMPTY);
      }
    }
  }

  public record GetWaitingQueuePositionByUuid(String uuid) {

    public GetWaitingQueuePositionByUuid {
      if (uuid == null || uuid.isEmpty()) {
        throw new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_UUID_MUST_NOT_BE_EMPTY);
      }
    }
  }

  public record FindDistinctConcertIdsByStatusQuery(WaitingQueueStatus status) {

    public FindDistinctConcertIdsByStatusQuery {
      if (status == null) {
        throw new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_STATUS_MUST_NOT_BE_NULL);
      }
    }
  }

  public record CountWaitingQueueByConcertIdAndStatusQuery(Long concertId,
                                                           WaitingQueueStatus status) {

    public CountWaitingQueueByConcertIdAndStatusQuery {
      if (concertId == null) {
        throw new CoreException(ErrorType.Concert.CONCERT_ID_MUST_NOT_BE_NULL);
      }
      if (status == null) {
        throw new CoreException(ErrorType.WaitingQueue.WAITING_QUEUE_STATUS_MUST_NOT_BE_NULL);
      }
    }
  }
}
