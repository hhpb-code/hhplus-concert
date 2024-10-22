package com.example.hhplus.concert.domain.waitingqueue.dto;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;

public class WaitingQueueCommand {

  public record CreateWaitingQueueCommand(Long concertId) {

    public CreateWaitingQueueCommand {
      if (concertId == null) {
        throw new CoreException(ErrorType.Concert.CONCERT_ID_MUST_NOT_BE_NULL);
      }
    }
  }

  public record ActivateWaitingQueuesCommand(Long concertId, Integer availableSlots) {

    public ActivateWaitingQueuesCommand {
      if (concertId == null) {
        throw new CoreException(ErrorType.Concert.CONCERT_ID_MUST_NOT_BE_NULL);
      }
      if (availableSlots == null) {
        throw new CoreException(ErrorType.WaitingQueue.AVAILABLE_SLOTS_MUST_NOT_BE_NULL);
      }
      if (availableSlots <= 0) {
        throw new CoreException(ErrorType.WaitingQueue.AVAILABLE_SLOTS_MUST_BE_POSITIVE);
      }
    }
  }
}
